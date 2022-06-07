import java.util.ArrayList;

//Top Abstract Product class : FileSystem
abstract class FileSystem{
    abstract public String displayName();
}

//First Abstract Product class : File
abstract class File extends FileSystem{
    protected String name;
    public String displayName(){
        return name;
    }
}

/* Concrete Product classes initialize the name variable appropriately for each file system type,
 to be used in their parent Abstract Product class' displayName methods */

//Concrete Product classes of Abstract Product class File : LinuxFile, BSDFile, NTFile
class LinuxFile extends File{
    public LinuxFile(){
        name = "Linux File";
        System.out.println("Linux file is created...");
    }
}

class BSDFile extends File{
    public BSDFile(){
        name = "BSD File";
        System.out.println("BSD file is created...");
    }
}

class NTFile extends File{
    public NTFile(){
        name = "NT File";
        System.out.println("NT file is created...");
    }
}

//Second Abstract Product class : Directory
abstract class Directory extends FileSystem{
    protected String name;
    public String displayName(){
        return name;
    }
}

//Concrete Product classes of Abstract Product class Directory : LinuxDirectory, BSDirectory, NTDirectory
class LinuxDirectory extends Directory{
    public LinuxDirectory(){
        name = "Linux Directory";
        System.out.println("Linux Directory is created...");
    }
}

class BSDDirectory extends Directory{
    public BSDDirectory(){
        name = "BSD Directory";
        System.out.println("BSD Directory is created...");
    }
}

class NTDirectory extends Directory{
    public NTDirectory(){
        name = "NT Directory";
        System.out.println("NT Directory is created...");
    }
}
//Abstract Factory class : SystemFactory
/*Since every system must have only one file system,
we must use Singleton Pattern to ensure we can control the instances getting created */
abstract class SystemFactory{
    abstract public File createFile();
    abstract public Directory createDirectory();
    protected static SystemFactory instance = null;

    public static SystemFactory getInstance() {
        if(instance != null) {
            System.out.println("Cannot create this instance!");
        }
        return instance;
    }
}
//Concrete Factory classes of Abstract Factory class SystemFactory : LinuxFactory, BSDFactory, NTFactory
/*Each Concrete Factory class overrides its parent's getInstance method to create their own factory instances, if possible */
class LinuxFactory extends SystemFactory{

    private LinuxFactory(){ }

    public static SystemFactory getInstance(){
        if (instance == null){
            instance = new LinuxFactory();
        }
        return instance;
    }
    public LinuxFile createFile() {
        return new LinuxFile();
    }
    public LinuxDirectory createDirectory() {
        return new LinuxDirectory();
    }
}

class BSDFactory extends SystemFactory{

    private BSDFactory(){ }

    public static SystemFactory getInstance(){
        if (instance == null){
            instance = new BSDFactory();
        }
        return instance;
    }

    public BSDFile createFile() {
        return new BSDFile();
    }
    public BSDDirectory createDirectory() {
        return new BSDDirectory();
    }
}

class NTFactory extends SystemFactory{
    private NTFactory(){ }

    public static SystemFactory getInstance(){
        if (instance == null){
            instance = new NTFactory();
        }
        return instance;
    }

    public NTFile createFile() {
        return new NTFile();
    }
    public NTDirectory createDirectory() {
        return new NTDirectory();
    }
}

//The Client class : BuildSystem
class BuildSystem{
    private ArrayList<FileSystem> fileSystems;
    public void createSystem(SystemFactory factory){
        fileSystems = new ArrayList<>();
        fileSystems.add(factory.createDirectory());
        fileSystems.add(factory.createFile());

    }

    public void displayFileSystem(){
        System.out.println("\tListing File System\n\t--------------");
        fileSystems.forEach(f -> System.out.println("\t" + f.displayName()));
    }
}
//Abstract Factory Method Design Pattern

public class AbstractFactory {
    public static void main(String[] args){
        //SystemFactory LINUX = LinuxFactory.getInstance();
        //SystemFactory NT = NTFactory.getInstance();
        SystemFactory BSD = BSDFactory.getInstance();

        BuildSystem file = new BuildSystem();
        System.out.println("Creating BSD");
        file.createSystem(BSD);
        file.displayFileSystem();
    }
}
//Target
interface DPlanguage {
    int fprintf (File handle, String str);
}
class LinuxFileWriteSystem {
    int uprintf(String str, File handle) {
        return uprintf(str,handle);
    }
}
class BSDFileWriteSystem {
    int uprintf(String str, File handle) {
        return uprintf(str,handle);
    }
}

class NTFileWriteSystem{
    public int printf(byte[] charArrray, File handle) {
        return printf(charArrray,handle);
    }
}

class AdaptorDPtoNT implements DPlanguage{
    @Override
    public int fprintf(File handle, String str) {


        int fileNT = ntFileWriteSystem.printf(handle, charArrray);
        return fileNT;
    }

    public AdaptorDPtoNT(NTFileWriteSystem _ntFileWriteSystem) {
        ntFileWriteSystem =_ntFileWriteSystem;
    }
    private NTFileWriteSystem ntFileWriteSystem;


    class AdaptorDPtoLinux implements DPlanguage{

        @Override
        public int fprintf(File handle, String str) {


            int fileLinux = linuxFileWriteSystem.uprintf(str, handle);
            return fileLinux;
        }

        public AdaptorDPtoLinux(LinuxFileWriteSystem _linuxFileWriteSystem) {
            linuxFileWriteSystem =_linuxFileWriteSystem;
        }
        private LinuxFileWriteSystem linuxFileWriteSystem;
    }

    class AdaptorDPtoBSD implements DPlanguage{

        @Override
        public int fprintf(File handle, String str) {


            int fileBSD = bSDFileWriteSystem.uprintf(str, handle);
            return fileBSD;
        }

        public AdaptorDPtoBSD(BSDFileWriteSystem _bSDFileWriteSystem) {
            bSDFileWriteSystem =_bSDFileWriteSystem;
        }
        private BSDFileWriteSystem bSDFileWriteSystem;
    }


}