/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.EmployeesConverter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.DocumentsManagerImpl;
import manager.impl.EmployeeDocumentsManagerImpl;
import manager.impl.EmployeesManagerImpl;
import manager.interfaces.IDocumentsManager;
import manager.interfaces.IEmployeeDocumentsManager;
import manager.interfaces.IEmployeesManager;
import mb.util.session.LoginS;
import orm.Documents;
import orm.EmployeeDocuments;
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeDocumentsView")
@ViewScoped
public class EmployeeDocumentsView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    /**
     * Creates a new instance of EmployeeSearchView
     */
    /*
     Employee 
     
     */
    private IEmployeesManager em = new EmployeesManagerImpl();
    private List<Employees> autocompleteSearchEmployeesList;
    private Employees selectedEmployee;
    private EmployeesConverter employeesConverter = new EmployeesConverter();
    /*
     Documents
     */
    private IDocumentsManager dm = new DocumentsManagerImpl();
    private List<Documents> employeeDocumentsToSubmit;
    /*
     Employee Document
     */
    private IEmployeeDocumentsManager edm = new EmployeeDocumentsManagerImpl();
    private List<EmployeeDocuments> submittedEmployeeDocuments;
    private List<EmployeeDocuments> allEmpExpDocuments;
    private EmployeeDocuments selectedEmpDocument = new EmployeeDocuments();
    private int count;

    /**
     * Creates a new instance of EmployeeDocumentsView
     */
    public EmployeeDocumentsView() {
        System.out.println("EmployeeDocumentsView");
    }
   @PostConstruct
   public void postConstruct()
   {
   this.retrieveAllExpDocuments();
   
   }
   
    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public List<Employees> getAutocompleteSearchEmployeesList() {
        return autocompleteSearchEmployeesList;
    }

    public void setAutocompleteSearchEmployeesList(List<Employees> autocompleteSearchEmployeesList) {
        this.autocompleteSearchEmployeesList = autocompleteSearchEmployeesList;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public EmployeesConverter getEmployeesConverter() {
        return employeesConverter;
    }

    public void setEmployeesConverter(EmployeesConverter employeesConverter) {
        this.employeesConverter = employeesConverter;
    }

    public List<Documents> getEmployeeDocumentsToSubmit() {
        return employeeDocumentsToSubmit;
    }

    public void setEmployeeDocumentsToSubmit(List<Documents> employeeDocumentsToSubmit) {
        this.employeeDocumentsToSubmit = employeeDocumentsToSubmit;
    }

    public List<EmployeeDocuments> getSubmittedEmployeeDocuments() {
        return submittedEmployeeDocuments;
    }

    public void setSubmittedEmployeeDocuments(List<EmployeeDocuments> submittedEmployeeDocuments) {
        this.submittedEmployeeDocuments = submittedEmployeeDocuments;
    }

    public IEmployeesManager getEm() {
        return em;
    }

    public void setEm(IEmployeesManager em) {
        this.em = em;
    }

    public IEmployeeDocumentsManager getEdm() {
        return edm;
    }

    public void setEdm(IEmployeeDocumentsManager edm) {
        this.edm = edm;
    }
    

    public List<EmployeeDocuments> getAllEmpExpDocuments() {
        return allEmpExpDocuments;
    }

    public void setAllEmpExpDocuments(List<EmployeeDocuments> allEmpExpDocuments) {
        this.allEmpExpDocuments = allEmpExpDocuments;
    }

    public EmployeeDocuments getSelectedEmpDocument() {
        return selectedEmpDocument;
    }

    public void setSelectedEmpDocument(EmployeeDocuments selectedEmpDocument) {
        this.selectedEmpDocument = selectedEmpDocument;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    //to retrieve all expired documents........created by pradeep
    
    public void retrieveAllExpDocuments() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
         try {
            HibernateUtil.beginTransaction();
            
            allEmpExpDocuments = edm.retrieveAllExpDocuments();
            HibernateUtil.commitTransaction();
         }catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        
        
        
    }
    
    //Employee Document Expirt count
    public void employeeDocExpCount(){
        
      try {
          
          HibernateUtil.beginTransaction();
          Iterator itr=edm.employeeDocExpCount().iterator();
          int i =1;
           while(itr.hasNext()){
              if(i==1){
                   this.count = ((Long)itr.next()).intValue();                                
                  break;
              }               
               i++;
           }
          
          HibernateUtil.commitTransaction();
      }  catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        
        
    }
    
    
    
    /*
     Employee Autocomplete method
     */
    public List<Employees> searchEmployeeForAutocomplete(String strSearchEmployeeCriteria) {

        try {
            HibernateUtil.beginTransaction();
            autocompleteSearchEmployeesList = em.searchEmployeeForAutocomplete(strSearchEmployeeCriteria);
            HibernateUtil.commitTransaction();
            employeesConverter.setSearchEmployees(autocompleteSearchEmployeesList);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return autocompleteSearchEmployeesList;
    }

    /*
     Not submitted Employee Documents List
     */
    public void retrieveNotSubmittedEmployeeDocument() {
        try {
            HibernateUtil.beginTransaction();
           // employeeDocumentsToSubmit = dm..retrieveNotSubmittedEmployeeDocumentList(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     Submitted employee documentsList
     */
    public void retrieveSubmittedEmployeeDocument() {
        try {
            HibernateUtil.beginTransaction();
            submittedEmployeeDocuments = edm.retrieveSubmittedEmployeeDocument(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
