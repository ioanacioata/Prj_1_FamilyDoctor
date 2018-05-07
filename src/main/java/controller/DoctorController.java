package controller;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import repository.Repository;
import validator.PatientValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorController {

    private List<Patient> PatientList;
    private List<Consultation> consultationList;
    private Repository rep;

    /**
     * Constructors
     */

    public DoctorController(Repository rep) {
        this.rep = rep;
        this.PatientList = rep.getPatientList();
        this.consultationList = rep.getConsultationList();
        // Get list from file in order to avoid duplicates.
    }

    /**
     * Getters
     */
    public List<Patient> getPatientList() {
        return PatientList;
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsulationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    public int getPatientBySSN(String SSN) {
        for (int i = 0; i < PatientList.size(); i++) {
            if (PatientList.get(i).getSsn().equals(SSN))
                return i;
        }

        return -1;
    }

    public int getConsByID(String ID) {
        for (int i = 0; i < consultationList.size(); i++) {
            if (consultationList.get(i).getConsID().compareTo(ID) == 0) {
                /*
                 * System.out.println("I proud to have found " + ID + " here: "
				 * + i); System.out.println("Proof : " +
				 * consultationList.get(i).toString());
				 */
                return i - 1;
            }
        }

        return -1;
    }

    public Repository getRepository() {
        return rep;
    }

    /**
     * Others
     */
    public void addPatient(Patient p) throws PatientException {
        if (p.getName() != null && p.getSsn() != null && p.getAddress() != null) {
            PatientValidation.nameValidate(p.getName());
            PatientValidation.ssnValidate(p.getSsn());
            PatientValidation.addressValidate(p.getAddress());
        } else {
            throw new PatientException("Null fields");
        }
        PatientList.add(p);
        try {
            rep.savePatientToFile(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adding of a new consultation for a patient (consultation date,
    // diagnostic, prescription drugs)

    public void addConsultation(String consID, String patientSSN, String diag,
                                List<String> meds, String date) throws ConsultationException {


        Consultation consultation = new Consultation(consID, patientSSN, diag, meds, date);             /*1*/
        if (consultation.getMeds() == null)                                                             /*2*/
            throw new ConsultationException("meds is null");                                            /*3*/

        if (consultation.getConsID() == null || consultation.getPatientSSN() == null
            || consultation.getDiag() == null || consultation.getMeds().size() == 0                     /*4*/
            || this.getPatientBySSN(consultation.getPatientSSN()) <= -1) {
            throw new ConsultationException("invalid arguments");                                       /*5*/
        }

        for (Consultation c : consultationList) {                                                       /*6*/
            if (c.equals(consultation)) {                                                               /*7*/
//                throw new ConsultationException("This consultation already exists");                    /*8*/
            }
        }
        consultationList.add(consultation);                                                             /*9*/
        try {
            rep.saveConsultationToFile(consultation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Patient p = new Patient();
        p = this.getPatientList().get(this.getPatientBySSN(consultation.getPatientSSN()));
        p.setConsultationId(p.getConsultationId() + 1);
    }                                                                                                   /*10*/

    public List<Patient> getPatientsWithDisease(String disease) throws PatientException {
        List<Consultation> c = this.getConsultationList();
        List<Patient> p = new ArrayList<Patient>();
        if (disease != null) {
            if (disease.length() == 0) {
                throw new PatientException("Empty disease provided");
            }
            int chk = 1;

            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getDiag().toLowerCase()
                    .contains(disease.toLowerCase())) // so that it is case
                // insensitive
                {
                    for (int j = 0; j < p.size(); j++) // verify patient was
                    // not already added
                    {
                        if (p.get(j).getSsn().equals(c.get(i).getPatientSSN())) {
                            chk = p.get(j).getConsultationId();
                        }
                    }

                    if (chk == 1) {
                        p.add(this.getPatientList().get(
                            this.getPatientBySSN(c.get(i).getPatientSSN()))); // get
                        // Patient
                        // by
                        // SSN
                    }
                    chk = 1;
                }
            }

            // Sort the list

            Patient paux = new Patient();

            for (int i = 0; i < p.size(); i++)
                for (int j = i + 1; j < p.size() - 1; j++)
                    if (p.get(j - 1).getConsultationId() < p.get(j).getConsultationId()) {
                        paux = p.get(j - 1);
                        p.set(j - 1, p.get(j));
                        p.set(j, paux);
                    }
        } else {
            throw new PatientException("Null disease parameter provided");
        }
        return p;
    }

	/*
     * For debugging purposes public void printList() { for (int i = 0; i <
	 * PatientList.size(); i++) {
	 * System.out.println(PatientList.get(i).toString()); } }
	 */
}
