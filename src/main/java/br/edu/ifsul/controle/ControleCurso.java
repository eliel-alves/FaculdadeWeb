/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.dao.CursoDAO;
import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.dao.EspecialidadeDAO;
import br.edu.ifsul.dao.InstituicaoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Aluno;
import br.edu.ifsul.modelo.Curso;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.modelo.Especialidade;
import br.edu.ifsul.modelo.Instituicao;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author eliel
 */
@Named(value = "controleCurso")
@ViewScoped
public class ControleCurso implements Serializable {
    
    @EJB
    private CursoDAO<Curso> dao;
    private Curso objeto;
    @EJB
    private InstituicaoDAO<Instituicao> daoInstituicao;
    @EJB
    private UsuarioDAO<Usuario> daoUsuario;
    private Disciplina disciplina;
    private Boolean novaDisciplina;
    private Integer tabAtiva;
    private Integer tabAtivaDisciplina;
    @EJB
    private AlunoDAO<Aluno> daoAluno;
    private Aluno aluno;
    
    public void salvarAluno() {
        if (!disciplina.getAlunos().contains(aluno)) {
            if (aluno.getNome().equals("")) {
                Util.mensagemErro("Selecione um aluno válido!");
            } else {
                disciplina.getAlunos().add(aluno);
                Util.mensagemInformacao("Aluno adicionado com sucesso!");
            }
        } else {
            Util.mensagemErro("Disciplina já possui este aluno!");
        }
    }

    public void excluirAluno(Aluno obj) {
        disciplina.getAlunos().remove(obj);
        Util.mensagemInformacao("Aluno removido com sucesso!");
    }
    
    public ControleCurso(){
        
    }
    
    public void novaDisciplina(){
        tabAtivaDisciplina = 0;
        disciplina = new Disciplina();
        novaDisciplina = true;
    }
    
    public void alterarDisciplina(Disciplina obj){
        tabAtivaDisciplina = 0;
        novaDisciplina = false;
        int index = objeto.getDisciplinas().indexOf(obj);
        disciplina = objeto.getDisciplinas().get(index);
    }
    
    public void salvarDisciplina(){
        if(novaDisciplina) {
            objeto.adicionarDisciplina(disciplina);
        }
        Util.mensagemInformacao("Disciplina adicionada ou alterada com sucesso!");
    }
    
    public void removerDisciplina(Disciplina obj){
        int index = objeto.getDisciplinas().indexOf(obj);
        objeto.removerDisciplina(index);
        Util.mensagemInformacao("Disciplina removida com sucesso!");
    }
    
    public String listar(){
        return "/privado/curso/listar?faces-redirect=true";
    }
    
    public void novo(){
        tabAtiva = 0;
        objeto = new Curso();
    }
    
    public void alterar(Object id){
        tabAtiva = 0;
        try {
            objeto = dao.getObjectByID(id);
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object id){
        try {
            objeto = dao.getObjectByID(id);
            dao.remove(objeto);
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

    public CursoDAO<Curso> getDao() {
        return dao;
    }

    public void setDao(CursoDAO<Curso> dao) {
        this.dao = dao;
    }

    public Curso getObjeto() {
        return objeto;
    }

    public void setObjeto(Curso objeto) {
        this.objeto = objeto;
    }

    public InstituicaoDAO<Instituicao> getDaoInstituicao() {
        return daoInstituicao;
    }

    public void setDaoInstituicao(InstituicaoDAO<Instituicao> daoInstituicao) {
        this.daoInstituicao = daoInstituicao;
    }

    public Integer getTabAtiva() {
        return tabAtiva;
    }

    public void setTabAtiva(Integer tabAtiva) {
        this.tabAtiva = tabAtiva;
    }

    public UsuarioDAO<Usuario> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(UsuarioDAO<Usuario> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Boolean getNovaDisciplina() {
        return novaDisciplina;
    }

    public void setNovaDisciplina(Boolean novaDisciplina) {
        this.novaDisciplina = novaDisciplina;
    }
    
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public AlunoDAO<Aluno> getDaoAluno() {
        return daoAluno;
    }

    public void setDaoAluno(AlunoDAO<Aluno> daoAluno) {
        this.daoAluno = daoAluno;
    }

    public Integer getTabAtivaDisciplina() {
        return tabAtivaDisciplina;
    }

    public void setTabAtivaDisciplina(Integer tabAtivaDisciplina) {
        this.tabAtivaDisciplina = tabAtivaDisciplina;
    }
    
}
