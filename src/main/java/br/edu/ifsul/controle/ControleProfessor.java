/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.dao.ProfessorDAO;
import br.edu.ifsul.dao.EspecialidadeDAO;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.modelo.Professor;
import br.edu.ifsul.modelo.Especialidade;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.HashMap;
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
    @EJB
    private DisciplinaDAO<Disciplina> daoDisciplina;
    private Integer tabAtiva;
    
    public ControleProfessor(){
        
    }
    
    public void imprimeProfessores(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioProfessores", parametros, dao.getListaTodos());
    }
    
    public String listar(){
        return "/privado/professor/listar?faces-redirect=true";
    }
    
    public void novo(){
        tabAtiva = 0;
        setObjeto(new Professor());
    }
    
    public void alterar(Object id){
        tabAtiva = 0;
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

    public Integer getTabAtiva() {
        return tabAtiva;
    }

    public void setTabAtiva(Integer tabAtiva) {
        this.tabAtiva = tabAtiva;
    }

    public DisciplinaDAO<Disciplina> getDaoDisciplina() {
        return daoDisciplina;
    }

    public void setDaoDisciplina(DisciplinaDAO<Disciplina> daoDisciplina) {
        this.daoDisciplina = daoDisciplina;
    }
    
}
