/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import br.edu.ifsul.modelo.Usuario;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author eliel
 */
@Stateful
public class UsuarioDAO<TIPO> extends DAOGenerico<Usuario> implements Serializable {

    public UsuarioDAO() {
        super();
        classePersistente = Usuario.class;

        // definir as ordens possíveis
        listaOrdem.add(new Ordem("nomeUsuario", "Nome de Usuário", "like"));
        listaOrdem.add(new Ordem("nome", "Nome", "like"));
        listaOrdem.add(new Ordem("email", "E-mail", "like"));

        // definir a ordem inicial
        ordemAtual = listaOrdem.get(0);

        //inicializa o conversor das ordens
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }

    @Override
    public Usuario getObjectByID(Object id) throws Exception {
        Usuario obj = em.find(Usuario.class, id);
        // uso para evitar o erro de lazy inicialization exception
        obj.getPermissoes().size();
        return obj;
    }

    public boolean usuarioExiste(String nomeUsuario) throws Exception {
        // query buscando nomes de usuário igual ao informado
        String jpql = "from Usuario where nomeUsuario = :pNomeUsuario";
        Query query = em.createQuery(jpql);
        query.setParameter("pNomeUsuario", nomeUsuario);

        // se resultado maior q 0 encontrou usuário
        if (query.getResultList().size() > 0) {
            return true;
        } else { // caso contrário usuario não existe
            return false;
        }
    }
}
