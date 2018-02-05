
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.GeneroFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sacvefor.Especie_svf;
import sacvefor.Especie_svfFacade;
import sacvefor.Familia_svf;
import sacvefor.Familia_svfFacade;
import sacvefor.Genero_svf;
import sacvefor.Genero_svfFacade;

/**
 * Temporal para importar las especies del SACVeFor
 * @deprecated Ya utilizada para realizar la migración
 * @author rincostante
 */
public class MbImport implements Serializable{

    private Familia_svf familiaSvf;
    private Genero_svf generoSvf;
    private Especie_svf especieSvf;
    private MbLogin login;
    private Usuario usLogeado;    
    
    
    @EJB
    private Familia_svfFacade familiaSvfFacade;
    @EJB
    private Genero_svfFacade generoSvfFacade;
    @EJB
    private Especie_svfFacade especieSvfFacade;
    @EJB
    private FamiliaFacade familiaFacade;
    @EJB
    private GeneroFacade generoFacade;
    @EJB
    private EspecieFacade especieFacade;
    
    
    public MbImport() {
    }
 
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */
    @PostConstruct
    public void init(){
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        if(login != null) usLogeado = login.getUsLogeado();   
    } 

    public void leerFammilias() throws IOException{
        File f = new File("D:\\rincostante\\Mis documentos\\SACVeFor\\Desarrollo\\trazabilidad\\planificación\\diseño\\ESPECIES\\import\\familia.xlsx");
        int fila = 0;
        if(f.exists()){ 
            try(FileInputStream fileInputStream = new FileInputStream(f)) {
                // Instancio un libro de excel
                XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
                // Obtengo la primera hoja del libro
                XSSFSheet hssfSheet = workBook.getSheetAt(0);
                // Levanto las filas de la hoja y las guardo en un iterator
                Iterator rowIterator = hssfSheet.rowIterator();
                // recorro las filas
                while(rowIterator.hasNext()){
                    familiaSvf = new Familia_svf();
                    int i = 0;
                    // obtengo los datos de cada fila y por cada una instancio e inserto un tmpEst
                    XSSFRow hssfRow = (XSSFRow) rowIterator.next();
                    // instancio otro iterator para guardar las celdas de la fila
                    Iterator cellIterator = hssfRow.cellIterator();
                    while(cellIterator.hasNext()){
                        XSSFCell xssfCell = (XSSFCell) cellIterator.next();
                        String valor = xssfCell.toString();
                        switch(i){
                            case 0:
                                if(valor.indexOf(".") > 0){
                                    familiaSvf.setId_svf(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                                }else{
                                    familiaSvf.setId_svf(Integer.valueOf(valor));
                                }
                                break;
                                
                            default:
                                familiaSvf.setNombre(valor);
                                break;
                        }
                        i += 1;
                    }
                    System.out.println(familiaSvf.getId_svf() + " | "
                            + "" + familiaSvf.getNombre());

                    familiaSvfFacade.create(familiaSvf);
                    System.out.println("-----------------guardó " + fila + "--------------");   
                    fila += 1;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }else{
            System.out.println("No se encontró el archvivo familia.xlsx");
        }
    }
    
    public void insertarFamilias() {
        try{
            // leo las familias importadas del SACVefor, no insertadas aún
            List<Familia_svf> lstFamSvf = familiaSvfFacade.getNoInsertadas();
            // recorro el listado y por cada una valido
            for(Familia_svf famSvf : lstFamSvf){
                // vrifico si existe la familia con el nombre de la leída
                Familia fam = familiaFacade.getXNombre(famSvf.getNombre());
                if(fam != null){
                    // si existe le pongo el flag esSacvefor en true y actualizo
                    fam.setEsSacvefor(true);
                    familiaFacade.edit(fam);
                    // actualizo el id_insertado en la Familia_svf
                    famSvf.setId_insertado(fam.getId());
                    familiaSvfFacade.edit(famSvf);
                    System.out.println("Se actualizó la familia " + famSvf.getNombre() + " en las dos tablas.");
                }else{
                    // si no existe lo inserto
                    fam = new Familia();
                    Date date = new Date(System.currentTimeMillis());
                    AdminEntidad admEnt = new AdminEntidad();
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsAlta(usLogeado);

                    fam.setAdminentidad(admEnt);
                    fam.setEsSacvefor(true);
                    fam.setNombre(famSvf.getNombre());

                    familiaFacade.create(fam);

                    // actualizo el id_insertado en la Familia_svf
                    famSvf.setId_insertado(fam.getId());
                    familiaSvfFacade.edit(famSvf);
                    System.out.println("Se insertó la familia " + famSvf.getNombre() + " y se actualizaron las dos tablas.");
                }
            }
        }catch(Exception ex){
            System.out.println("Hubo un error insertando Familias " + ex.getMessage());
        }
    }
    
    public void leerGeneros(){
        File f = new File("D:\\rincostante\\Mis documentos\\SACVeFor\\Desarrollo\\trazabilidad\\planificación\\diseño\\ESPECIES\\import\\genero.xlsx");
        int fila = 0;
        if(f.exists()){ 
            try(FileInputStream fileInputStream = new FileInputStream(f)) {
                // Instancio un libro de excel
                XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
                // Obtengo la primera hoja del libro
                XSSFSheet hssfSheet = workBook.getSheetAt(0);
                // Levanto las filas de la hoja y las guardo en un iterator
                Iterator rowIterator = hssfSheet.rowIterator();
                // recorro las filas
                while(rowIterator.hasNext()){
                    generoSvf = new Genero_svf();
                    int i = 0;
                    // obtengo los datos de cada fila y por cada una instancio e inserto un tmpEst
                    XSSFRow hssfRow = (XSSFRow) rowIterator.next();
                    // instancio otro iterator para guardar las celdas de la fila
                    Iterator cellIterator = hssfRow.cellIterator();
                    while(cellIterator.hasNext()){
                        XSSFCell xssfCell = (XSSFCell) cellIterator.next();
                        String valor = xssfCell.toString();
                        switch(i){
                            case 0:
                                if(valor.indexOf(".") > 0){
                                    generoSvf.setId_svf(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                                }else{
                                    generoSvf.setId_svf(Integer.valueOf(valor));
                                }
                                break;
                                
                            case 1:
                                generoSvf.setNombre(valor);
                                break;
                            default:
                                if(valor.indexOf(".") > 0){
                                    generoSvf.setId_familia_svf(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                                }else{
                                    generoSvf.setId_familia_svf(Integer.valueOf(valor));
                                }
                                break;
                        }
                        i += 1;
                    }
                    System.out.println(generoSvf.getId_svf() + " | "
                            + "" + generoSvf.getNombre());
                    
                    // seteo la familia_svf
                    Familia_svf famSvf = familiaSvfFacade.getById_svf(generoSvf.getId_familia_svf());
                    generoSvf.setFamilia_svf(famSvf);
                    
                    generoSvfFacade.create(generoSvf);
                    System.out.println("-----------------guardó " + fila + "--------------");   
                    fila += 1;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }else{
            System.out.println("No se encontró el archvivo genero.xlsx");
        }
    }
    
    public void insertarGeneros(){
        try{
            // leo los géneros importados del SACVefor, no insertados aún
            List<Genero_svf> lstGenSvf = generoSvfFacade.getNoInsertados();
            // recorro el listado y por cada una valido
            for(Genero_svf genSvf : lstGenSvf){
                // vrifico si existe el Género con el nombre del leído
                Genero gen = generoFacade.getXNombre(genSvf.getNombre());
                if(gen != null){
                    // si existe le pongo el flag esSacvefor en true y actualizo
                    gen.setEsSacvefor(true);
                    generoFacade.edit(gen);
                    // actualizo el id_insertado en el Genero_svf
                    genSvf.setId_insertado(gen.getId());
                    generoSvfFacade.edit(genSvf);
                    System.out.println("Se actualizó el género " + genSvf.getNombre() + " en las dos tablas.");
                }else{
                    // si no existe lo inserto
                    gen = new Genero();
                    Date date = new Date(System.currentTimeMillis());
                    AdminEntidad admEnt = new AdminEntidad();
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsAlta(usLogeado);
                    // seteo el admin
                    gen.setAdminentidad(admEnt);
                    gen.setEsSacvefor(true);
                    gen.setNombre(genSvf.getNombre());
                    
                    // busco la familia desde el id_insertado de la vinculada al género
                    Familia fam = familiaFacade.find(genSvf.getFamilia_svf().getId_insertado());
                    if(fam != null){
                        gen.setFamilia(fam);
                        generoFacade.create(gen);
                    }
                    
                    // actualizo el id_insertado en la Familia_svf
                    if(gen.getId() != null){
                        genSvf.setId_insertado(gen.getId());
                        generoSvfFacade.edit(genSvf);
                        System.out.println("Se insertó el género " + genSvf.getNombre() + " y se actualizaron las dos tablas.");
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("Hubo un error insertando Géneros " + ex.getMessage());
        }
    }
    
    public void leerEspecies(){
        File f = new File("D:\\rincostante\\Mis documentos\\SACVeFor\\Desarrollo\\trazabilidad\\planificación\\diseño\\ESPECIES\\import\\especie.xlsx");
        int fila = 0;
        if(f.exists()){ 
            try(FileInputStream fileInputStream = new FileInputStream(f)) {
                // Instancio un libro de excel
                XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
                // Obtengo la primera hoja del libro
                XSSFSheet hssfSheet = workBook.getSheetAt(0);
                // Levanto las filas de la hoja y las guardo en un iterator
                Iterator rowIterator = hssfSheet.rowIterator();
                // recorro las filas
                while(rowIterator.hasNext()){
                    especieSvf = new Especie_svf();
                    int i = 0;
                    // obtengo los datos de cada fila y por cada una instancio e inserto un tmpEst
                    XSSFRow hssfRow = (XSSFRow) rowIterator.next();
                    // instancio otro iterator para guardar las celdas de la fila
                    Iterator cellIterator = hssfRow.cellIterator();
                    while(cellIterator.hasNext()){
                        XSSFCell xssfCell = (XSSFCell) cellIterator.next();
                        String valor = xssfCell.toString();
                        switch(i){
                            case 0:
                                if(valor.indexOf(".") > 0){
                                    especieSvf.setId_svf(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                                }else{
                                    especieSvf.setId_svf(Integer.valueOf(valor));
                                }
                                break;
                                
                            case 1:
                                especieSvf.setNombre_vulgar(valor);
                                break;
                            case 2:
                                especieSvf.setObs(valor);
                                break;
                            case 3:
                                especieSvf.setNombre(valor);
                                break;
                            default:
                                if(valor.indexOf(".") > 0){
                                    especieSvf.setId_genero_svf(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                                }else{
                                    especieSvf.setId_genero_svf(Integer.valueOf(valor));
                                }
                                break;
                        }
                        i += 1;
                    }
                    // seteo la familia_svf
                    Genero_svf genSvf = generoSvfFacade.getById_svf(especieSvf.getId_genero_svf());
                    especieSvf.setGenero_svf(genSvf);
                    
                    System.out.println(especieSvf.getId_svf() + " | "
                            + "" + especieSvf.getNombre());
                    
                    especieSvfFacade.create(especieSvf);
                    System.out.println("-----------------guardó " + fila + "--------------");   
                    fila += 1;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }else{
            System.out.println("No se encontró el archvivo especie.xlsx");
        }
    }
    
    public void insertarEspecies(){
        try{
            // leo las especies importados del SACVefor, no insertadas aún
            List<Especie_svf> lstEspSvf = especieSvfFacade.getNoInsertadas();
            // recorro el listado y por cada una valido
            for(Especie_svf espSvf : lstEspSvf){
                // vrifico si existe la especie con el nombre de la leída
                Especie esp = especieFacade.getXNombre(espSvf.getNombre());
                if(esp != null){
                    // si existe le pongo el flag esSacvefor en true y actualizo
                    esp.setEsSacvefor(true);
                    especieFacade.edit(esp);
                    // actualizo el id_insertado en el Genero_svf
                    espSvf.setId_insertado(esp.getId());
                    especieSvfFacade.edit(espSvf);
                    System.out.println("Se actualizó la especie " + espSvf.getNombre() + " en las dos tablas.");
                }else{
                    // si no existe la inserto
                    esp = new Especie();
                    Date date = new Date(System.currentTimeMillis());
                    AdminEntidad admEnt = new AdminEntidad();
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsAlta(usLogeado);
                    // seteo el admin
                    esp.setAdminentidad(admEnt);
                    esp.setEsSacvefor(true);
                    esp.setNombre(espSvf.getNombre());
                    
                    // busco el género desde el id_insertado del vinculado a la especie
                    Genero gen = generoFacade.find(espSvf.getGenero_svf().getId_insertado());
                    if(gen != null){
                        esp.setGenero(gen);
                        especieFacade.create(esp);
                    }
                    
                    // actualizo el id_insertado en el Genero_svf
                    if(esp.getId() != null){
                        espSvf.setId_insertado(esp.getId());
                        especieSvfFacade.edit(espSvf);
                        System.out.println("Se insertó la especie " + espSvf.getNombre() + " y se actualizaron las dos tablas.");
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("Hubo un error insertando Especies " + ex.getMessage());
        }
    }
}
