/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.ContactTypesConverter;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import manager.impl.ContactsManagerImpl;
import manager.interfaces.IContactsManager;
import orm.Contacts;
import util.HibernateUtil;
import converter.ContactsConverter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import manager.impl.ContactTypesManagerImpl;
import manager.interfaces.IContactTypesManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import orm.ContactTypes;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "contactsSearchView")
@ViewScoped
public class ContactsSearchView implements Serializable {

    private IContactsManager cm = new ContactsManagerImpl();
    /*
     Contacts   
     */
    private List<Contacts> contactList;
    private Contacts selectedContactToAdd;
    List<Contacts> autocompleteSearchContactsList;
    private Contacts autocompleteSelectedContactSearch;
    private IContactTypesManager ctm = new ContactTypesManagerImpl();
    private List<ContactTypes> contactTypesWithoutEmployeeContact;
    private ContactTypes selectedContactType = new ContactTypes();
    /* ContactTypes converter */
    private ContactTypesConverter contactTypesConverter = new ContactTypesConverter();
    /* Contact converter*/
    private ContactsConverter contactsConverter = new ContactsConverter();

    /**
     * Creates a new instance of ContactSearchView
     */
    public ContactsSearchView() {
        System.out.println("ContactsSearchView");
        try {

            HibernateUtil.beginTransaction();
            contactTypesWithoutEmployeeContact = ctm.retrieveContactTypewithoutEmployeeContactType(1);
            contactList = cm.retrieveAllLimitedContacts();
            HibernateUtil.commitTransaction();
            contactTypesConverter.setSearchContactTypes(contactTypesWithoutEmployeeContact);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    public IContactsManager getCm() {
        return cm;
    }

    public void setCm(IContactsManager cm) {
        this.cm = cm;
    }

    public List<Contacts> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contacts> contactList) {
        this.contactList = contactList;
    }

    public Contacts getAutocompleteSelectedContactSearch() {
        return autocompleteSelectedContactSearch;
    }

    public void setAutocompleteSelectedContactSearch(Contacts autocompleteSelectedContactSearch) {
        this.autocompleteSelectedContactSearch = autocompleteSelectedContactSearch;
    }

    public Contacts getSelectedContactToAdd() {
        return selectedContactToAdd;
    }

    public void setSelectedContactToAdd(Contacts selectedContactToAdd) {
        this.selectedContactToAdd = selectedContactToAdd;
    }

    public ContactsConverter getContactsConverter() {
        return contactsConverter;
    }

    public void setContactsConverter(ContactsConverter contactConverter) {
        this.contactsConverter = contactConverter;
    }

    public List<ContactTypes> getContactTypesWithoutEmployeeContact() {
        return contactTypesWithoutEmployeeContact;
    }

    public void setContactTypesWithoutEmployeeContact(List<ContactTypes> contactTypesWithoutEmployeeContact) {
        this.contactTypesWithoutEmployeeContact = contactTypesWithoutEmployeeContact;
    }

    public ContactTypes getSelectedContactType() {
        return selectedContactType;
    }

    public void setSelectedContactType(ContactTypes selectedContactType) {
        this.selectedContactType = selectedContactType;
    }

    public ContactTypesConverter getContactTypesConverter() {
        return contactTypesConverter;
    }

    public void setContactTypesConverter(ContactTypesConverter contactTypesConverter) {
        this.contactTypesConverter = contactTypesConverter;
    }

    public IContactTypesManager getCtm() {
        return ctm;
    }

    public void setCtm(IContactTypesManager ctm) {
        this.ctm = ctm;
    }


    /*
     Contacts Autocomplete method
     */
    public List<Contacts> searchContactForAutocomplete(String strSearchContactCriteria) {

        try {
            HibernateUtil.beginTransaction();
            autocompleteSearchContactsList = cm.searchContactForAutocomplete(strSearchContactCriteria);

            HibernateUtil.commitTransaction();
            contactsConverter.setSearchContacts(autocompleteSearchContactsList);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return autocompleteSearchContactsList;
    }

    /*
     Search contact method
     */
    public void getAutocompleteSelectedContactContactFromDB() {
        try {
            HibernateUtil.beginTransaction();
            contactList = cm.searchContactById(autocompleteSelectedContactSearch.getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }


    /*
     Add contact
     */
    public void addContact() {
        RequestContext req = null;
        try {

            req = RequestContext.getCurrentInstance();
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            HibernateUtil.beginTransaction();
            cm.saveNew(selectedContactToAdd);
            HibernateUtil.commitTransaction();

            clearSelectedContactToAdd();

            String message = text.getString("ui.Bean.contactAddSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.update(":frmContactsTable");
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

        getCurrentLimitedContacts();
        req = null;
    }
    /*
     Update contact
     */

    public void updateContact() {
        RequestContext req = null;
        try {

            req = RequestContext.getCurrentInstance();
            FacesContext context = FacesContext.getCurrentInstance();

            HibernateUtil.beginTransaction();
            cm.updateExisting(selectedContactToAdd);
            HibernateUtil.commitTransaction();

            clearSelectedContactToAdd();
            String message = "Contact updated successfully.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.execute("DlgContactsConfirm.hide();");
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        getCurrentLimitedContacts();
        req = null;
    }


    /*
     To Add contact set selected
     */
    public void clearSelectedContactToAdd() {
        selectedContactToAdd = null;
        selectedContactToAdd = new Contacts();


    }

    private void getCurrentLimitedContacts() {
        try {

            HibernateUtil.beginTransaction();
            contactList = cm.retrieveAllLimitedContacts();
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();

        } finally {
            HibernateUtil.closeSession();

        }
    }
}
