package repository_impl;

//import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Identifiable;


public abstract class   FileRepository<T extends Identifiable> extends MemoryRepository<T> {
    protected String file;
    //protected ObjectMapper objectMapper;
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public FileRepository(String file) {
        this.file = file;
        //this.objectMapper = new ObjectMapper();
        readfromfile();
    }

    protected abstract void readfromfile();
    protected abstract void writefromfile();

    public void addElem(T elem) //throws      //DuplicateEntityException{
    {   super.save(elem);
        writefromfile();
    }
    public void deleteElem(long id){
        super.delete(id);
        writefromfile();
    }

    public void modify(long id){
        //super.modify(id);
        writefromfile();
    }

    
}
