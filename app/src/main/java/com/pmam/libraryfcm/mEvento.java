package com.pmam.libraryfcm;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class mEvento {

    private Boolean stop_abandonment = false;

    private String
            idDocBancoHoras,
            idDoc,
            nomeCreate,
            nomeUpdate,
            obs,
            nomeEvento,
            responsavelEvento,
            uidCreate,
            uidUpdate;
    private int
            qtdFaltas;
    private int qtdVagas;

    public String getIdDocBancoHoras() {
        return idDocBancoHoras;
    }

    public void setIdDocBancoHoras(String idDocBancoHoras) {
        this.idDocBancoHoras = idDocBancoHoras;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getNomeCreate() {
        return nomeCreate;
    }

    public void setNomeCreate(String nomeCreate) {
        this.nomeCreate = nomeCreate;
    }

    public String getNomeUpdate() {
        return nomeUpdate;
    }

    public void setNomeUpdate(String nomeUpdate) {
        this.nomeUpdate = nomeUpdate;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getResponsavelEvento() {
        return responsavelEvento;
    }

    public void setResponsavelEvento(String responsavelEvento) {
        this.responsavelEvento = responsavelEvento;
    }

    public String getUidCreate() {
        return uidCreate;
    }

    public void setUidCreate(String uidCreate) {
        this.uidCreate = uidCreate;
    }

    public String getUidUpdate() {
        return uidUpdate;
    }

    public void setUidUpdate(String uidUpdate) {
        this.uidUpdate = uidUpdate;
    }

    public int getQtdFaltas() {
        return qtdFaltas;
    }

    public void setQtdFaltas(int qtdFaltas) {
        this.qtdFaltas = qtdFaltas;
    }

    public int getQtdVagas() {
        return qtdVagas;
    }

    public void setQtdVagas(int qtdVagas) {
        this.qtdVagas = qtdVagas;
    }

    public int getQtdEscalas() {
        return qtdEscalas;
    }

    public void setQtdEscalas(int qtdEscalas) {
        this.qtdEscalas = qtdEscalas;
    }

    public int getQtdConfirmado() {
        return qtdConfirmado;
    }

    public void setQtdConfirmado(int qtdConfirmado) {
        this.qtdConfirmado = qtdConfirmado;
    }

    public int getQtdVoluntarios() {
        return qtdVoluntarios;
    }

    public void setQtdVoluntarios(int qtdVoluntarios) {
        this.qtdVoluntarios = qtdVoluntarios;
    }

    public Timestamp getDateTimeEvento() {
        return dateTimeEvento;
    }

    public void setDateTimeEvento(Timestamp dateTimeEvento) {
        this.dateTimeEvento = dateTimeEvento;
    }

    public Timestamp getDateTimeCreate() {
        return dateTimeCreate;
    }

    public void setDateTimeCreate(Timestamp dateTimeCreate) {
        this.dateTimeCreate = dateTimeCreate;
    }

    public Timestamp getDateTimeUpdate() {
        return dateTimeUpdate;
    }

    public void setDateTimeUpdate(Timestamp dateTimeUpdate) {
        this.dateTimeUpdate = dateTimeUpdate;
    }

    public ArrayList<Integer> getOpm_ids() {
        return opm_ids;
    }

    public void setOpm_ids(ArrayList<Integer> opm_ids) {
        this.opm_ids = opm_ids;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    private int qtdEscalas;
    private int qtdConfirmado;
    private int qtdVoluntarios;
    private Timestamp
            dateTimeEvento,
            dateTimeCreate,
            dateTimeUpdate;
    private ArrayList<Integer> opm_ids;
    private boolean published;

    public mEvento(){}


    public Boolean getStop_abandonment() {
        return stop_abandonment;
    }

    public void setStop_abandonment(Boolean stop_abandonment) {
        this.stop_abandonment = stop_abandonment;
    }

    @NonNull
    @Override
    public String toString() {
        return "mEvento{" +
                "stop_abandonment=" + stop_abandonment +
                ", idDocBancoHoras='" + idDocBancoHoras + '\'' +
                ", idDoc='" + idDoc + '\'' +
                ", nomeCreate='" + nomeCreate + '\'' +
                ", nomeUpdate='" + nomeUpdate + '\'' +
                ", obs='" + obs + '\'' +
                ", nomeEvento='" + nomeEvento + '\'' +
                ", responsavelEvento='" + responsavelEvento + '\'' +
                ", uidCreate='" + uidCreate + '\'' +
                ", uidUpdate='" + uidUpdate + '\'' +
                ", qtdFaltas=" + qtdFaltas +
                ", qtdVagas=" + qtdVagas +
                ", qtdEscalas=" + qtdEscalas +
                ", qtdConfirmado=" + qtdConfirmado +
                ", qtdVoluntarios=" + qtdVoluntarios +
                ", dateTimeEvento=" + dateTimeEvento +
                ", dateTimeCreate=" + dateTimeCreate +
                ", dateTimeUpdate=" + dateTimeUpdate +
                ", opm_ids=" + opm_ids +
                ", published=" + published +
                '}';
    }
}
