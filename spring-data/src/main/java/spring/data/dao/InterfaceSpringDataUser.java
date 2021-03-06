package spring.data.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.data.model.UsuarioSpringData;

@Repository
public interface InterfaceSpringDataUser extends CrudRepository<UsuarioSpringData, Long> {
	
	/*Busca do banco aonde contem o valor do parametro*/
	@Query(value="select p from UsuarioSpringData p where p.nome like %?1%")
	public List<UsuarioSpringData> buscaPorNome (String nome);
	
	/*Busca do banco igual o parametro*/
	@Lock(LockModeType.READ)/*Se um usuario tiver consultando no sistema e outro usuario estiver modificando, ele nao vai deixar atualizar se estivar sendo consultado*/
	@Query(value = "select p from UsuarioSpringData p where p.nome = :paramnome")
	public UsuarioSpringData buscaPorNomeParam (@Param("paramnome") String paramnome);
	
	@Modifying/*Identifica que essa operação modificará o banco*/
	@Transactional/*Abre uma transação*/
	@Query("delete from UsuarioSpringData u where u.nome = ?1")
	public void deletePorNome(String nome);
	
	@Modifying
	@Transactional
	@Query("update UsuarioSpringData u set u.email = ?1 where u.nome = ?2")
	public void updateEmailPorNome(String email, String nome);
}
