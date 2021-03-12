/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ProfessorDAO;
import br.edu.ifsul.dao.EspecialidadeDAO;
import br.edu.ifsul.modelo.Professor;
import br.edu.ifsul.modelo.Especialidade;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author eliel
 */
@Named(value = "controleProfessor")
@ViewScoped
public class ControleProfessor implements Serializable {
    
    @EJB
    private ProfessorDAO<Professor> dao;
    private Professor objeto;
    @EJB
    private EspecialidadeDAO<Especialidade> daoEspecialidade;
    
    public ControleProfessor(){
        
    }
    
    public String listar(){
        return "/privado/professor/listar?faces-redirect=true";
    }
    
    public void novo(){
        setObjeto(new Professor());
    }
    
    public void alterar(Object id){
        try {
            setObjeto(getDao().getObjectByID(id));
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object id){
        try {
            setObjeto(getDao().getObjectByID(id));
            getDao().remove(getObjeto());
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao remover o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if(getObjeto().getId() == null){
                getDao().persist(getObjeto());
            }else{
                getDao().merge(getObjeto());
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao salvar o objeto: " + Util.getMensagemErro(e));
        }
    }

    public ProfessorDAO<Professor> getDao() {
        return dao;
    }

    public void setDao(ProfessorDAO<Professor> dao) {
        this.dao = dao;
    }

    public Professor getObjeto() {
        return objeto;
    }

    public void setObjeto(Professor objeto) {
        this.objeto = objeto;
    }

    public EspecialidadeDAO<Especialidade> getDaoEspecialidade() {
        return daoEspecialidade;
    }

    public void setDaoEspecialidade(EspecialidadeDAO<Especialidade> daoEspecialidade) {
        this.daoEspecialidade = daoEspecialidade;
    }
    
}
