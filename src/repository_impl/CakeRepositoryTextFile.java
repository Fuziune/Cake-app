package repository_impl;

import domain.Cake;

import java.io.*;

public class CakeRepositoryTextFile extends FileRepository<Cake> {
    public CakeRepositoryTextFile(String file) {
        super(file);
    }

    @Override
    protected void readfromfile() {
        try{
            BufferedReader reader=new BufferedReader(new FileReader(this.file));
            String line=null;
            while((line=reader.readLine())!=null){
                String[] token=line.split("[,]");
                if(token.length!=4)
                    continue;
                else{
                    Long Id=Long.parseLong(token[0]);
                    String flavor=token[1].trim();
                    String size=token[2].trim();
                    Double price=Double.parseDouble(token[3]);
                    Cake cake=new Cake(Id,flavor,size,price);
                    super.save(cake);
                }
            }
            reader.close();
        }
        catch (FileNotFoundException f){
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void writefromfile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file))) {
            Iterable<Cake> cakes = this.getAll();
            for (Cake cake : cakes) {
                //System.out.println(cake);
                writer.write(cake.getId() + "," + cake.getFlavor() + "," + cake.getSize() + "," + cake.getPrice());
                writer.write('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
