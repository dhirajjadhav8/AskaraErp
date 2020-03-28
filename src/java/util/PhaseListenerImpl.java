/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.InternalCommunicationDAO;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.primefaces.context.RequestContext;
import orm.InternalCommunication;

/**
 *
 * @author dhirajj
 */
public class PhaseListenerImpl implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        System.out.println("After Executing " + event.getPhaseId());
        RequestContext.getCurrentInstance().update(":frmOutAdd");
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        InternalCommunicationDAO internalCommunicationDAO = (InternalCommunicationDAO) facesContext.getApplication().createValueBinding("#{internalCommunicationDAO}").getValue(facesContext);
        if (event.getPhaseId().getOrdinal() == 3) {
            if (internalCommunicationDAO.getSelectedInterCommOutAdd().getDetails() == null || "".equals(internalCommunicationDAO.getSelectedInterCommOutAdd().getDetails())) {
                internalCommunicationDAO.getSelectedInterCommOutAdd().setDetails(" ");
                RequestContext.getCurrentInstance().update(":frmOutAdd");
            }

//            char c = 0;
//            c = internalCommunicationDAO.getSelectedInterCommOutAdd().getMessagetype();
//
//            if ('\0' == c) {

//            internalCommunicationDAO.getSelectedInterCommOutAdd().setMessagetype(' ');
        }
//        }
        System.out.println("Before Executing " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.PROCESS_VALIDATIONS;
    }
}
