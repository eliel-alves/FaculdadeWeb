/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.modelo.Aluno;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author eliel
 */
@Named(value = "controleDisciplina")
@ViewScoped
public class ControleDisciplina implements Serializable {

    @EJB
    private DisciplinaDAO<Disciplina> dao;
    private Disciplina objeto;
    private Integer tabAtiva;
    @EJB
    private AlunoDAO<Aluno> daoAluno;
    private Aluno aluno;

    public ControleDisciplina() {

    }

    public String listar() {
        return "/privado/disciplina/listar?faces-redirect=true";
    }
    
    public void salvarAluno() {
        if (!objeto.getAlunos().contains(aluno)) {
            if (aluno.getNome().equals("")) {
                Util.mensagemErro("Selecione um aluno válido!");
            } else {
                objeto.getAlunos().add(aluno);
                Util.mensagemInformacao("Aluno adicionado com sucesso!");
            }
        } else {
            Util.mensagemErro("Disciplina já possui este aluno!");
        }
    }

    public void excluirAluno(Aluno obj) {
        objeto.getAlunos().remove(obj);
        Util.mensagemInformacao("Aluno removido com sucesso!");
    }

    public void novo() {
        tabAtiva = 0;
        objeto = new Disciplina();
    }

    public void alterar(Object id) {
        tabAtiva = 0;
        try {
            objeto = dao.getObjectByID(id);
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }

    public void excluir(Object id) {
        try {
            objeto = dao.getObjectByID(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao remover o objeto: " + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if(objeto.getId() == null){
                dao.persist(getObjeto());
            }else{
                dao.merge(getObjeto());
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao salvar o objeto: " + Util.getMensagemErro(e));
        }
    }

    public DisciplinaDAO<Disciplina> getDao() {
        return dao;
    }

    public void setDao(DisciplinaDAO<Disciplina> dao) {
        this.dao = dao;
    }

    public Disciplina getObjeto() {
        return objeto;
    }

    public void setObjeto(Disciplina objeto) {
        this.objeto = objeto;
    }
    
    public Integer getTabAtiva() {
        return tabAtiva;
    }

    public void setTabAtiva(Integer tabAtiva) {
        this.tabAtiva = tabAtiva;
    }

    public AlunoDAO<Aluno> getDaoAluno() {
        return daoAluno;
    }

    public void setDaoAluno(AlunoDAO<Aluno> daoAluno) {
        this.daoAluno = daoAluno;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }


}
