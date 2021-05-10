/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PermissaoDAO;
import br.edu.ifsul.modelo.Permissao;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author eliel
 */
@Named(value = "controlePermissao")
@ViewScoped
public class ControlePermissao implements Serializable {
    
    @EJB
    private PermissaoDAO<Permissao> dao;
    private Permissao objeto;
    private Boolean novoObjeto;
    
    public ControlePermissao(){
        
    }
    
    @RolesAllowed("ACESSAR")
    public String listar(){
        novoObjeto = false;
        return "/privado/permissao/listar?faces-redirect=true";
    }
    
    public void novo(){
        novoObjeto = true;
        objeto = new Permissao();
    }
    
    public void alterar(Object id){
        try {
            novoObjeto = false;
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
            if (novoObjeto){
                dao.persist(objeto);
            }else{
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        } catch (Exception e) {
            Util.mensagemInformacao("Erro ao salvar o objeto: " + Util.getMensagemErro(e));
        }
    }

    public PermissaoDAO<Permissao> getDao() {
        return dao;
    }

    public void setDao(PermissaoDAO<Permissao> dao) {
        this.dao = dao;
    }

    public Permissao getObjeto() {
        return objeto;
    }

    public void setObjeto(Permissao objeto) {
        this.objeto = objeto;
    }
    
    public Boolean getNovoObjeto() {
        return novoObjeto;
    }

    public void setNovoObjeto(Boolean novoObjeto) {
        this.novoObjeto = novoObjeto;
    }
    
    
    
}
