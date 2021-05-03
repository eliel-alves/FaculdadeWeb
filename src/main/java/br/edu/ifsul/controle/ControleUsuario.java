/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PermissaoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Permissao;
import br.edu.ifsul.modelo.Usuario;
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
@Named(value = "controleUsuario")
@ViewScoped
public class ControleUsuario implements Serializable {

    @EJB
    private UsuarioDAO<Usuario> dao;
    private Usuario objeto;
    @EJB
    private PermissaoDAO<Permissao> daoPermissao;
    private Permissao permissao;
    private Boolean novoUsuario = false;
    private Integer tabAtiva;

    public ControleUsuario() {

    }

    public void salvarPermissao() {
        if (!objeto.getPermissoes().contains(permissao)) {
            if (permissao.getNome().equals("")) {
                Util.mensagemErro("Selecione uma permissão válida!");
            } else {
                objeto.getPermissoes().add(permissao);
                Util.mensagemInformacao("Permissão adicionada com sucesso!");
            }
        } else {
            Util.mensagemErro("Usuário já possui esta permissão!");
        }
    }

    public void excluirPermissao(Permissao obj) {
        objeto.getPermissoes().remove(obj);
        Util.mensagemInformacao("Permissão removida com sucesso!");
    }

    public String listar() {
        novoUsuario = false;
        return "/privado/usuario/listar?faces-redirect=true";
    }

    public void novo() {
        tabAtiva = 0;
        novoUsuario = true;
        objeto = new Usuario();
    }

    public void alterar(Object id) {
        tabAtiva = 0;
        try {
            novoUsuario = false;
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
            if (novoUsuario) {
                //if(objeto.getNomeUsuario() == null){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }

            Util.mensagemInformacao("Objeto persistido com sucesso!");
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao salvar o objeto: " + Util.getMensagemErro(e));
        }
    }

    public void verificaExistenciaUsuario() {
        if (novoUsuario) {
            try {
                if (dao.usuarioExiste(objeto.getNomeUsuario())) {
                    Util.mensagemErro("O nome de usuário '" + objeto.getNomeUsuario() + "' já existe!");
                    objeto.setNomeUsuario(null);

                    // pegar componente que chamou o método
                    UIComponent comp = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());

                    if (comp != null) {
                        // se diferente de nulo faz um cast no componente para um input 
                        // colocando o setValid(false) para o campo ficar em vermelho após o update
                        UIInput input = (UIInput) comp;
                        input.setValid(false);
                    }
                }
            } catch (Exception e) {
                Util.mensagemErro("Erro do sistema: " + Util.getMensagemErro(e));
            }
        }
    }

    public UsuarioDAO<Usuario> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<Usuario> dao) {
        this.dao = dao;
    }

    public Usuario getObjeto() {
        return objeto;
    }

    public void setObjeto(Usuario objeto) {
        this.objeto = objeto;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public PermissaoDAO<Permissao> getDaoPermissao() {
        return daoPermissao;
    }

    public void setDaoPermissao(PermissaoDAO<Permissao> daoPermissao) {
        this.daoPermissao = daoPermissao;
    }

    public Boolean getNovoUsuario() {
        return novoUsuario;
    }

    public void setNovoUsuario(Boolean novoUsuario) {
        this.novoUsuario = novoUsuario;
    }
    
    public Integer getTabAtiva() {
        return tabAtiva;
    }

    public void setTabAtiva(Integer tabAtiva) {
        this.tabAtiva = tabAtiva;
    }

}
