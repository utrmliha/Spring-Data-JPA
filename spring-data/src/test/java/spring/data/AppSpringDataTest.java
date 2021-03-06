package spring.data;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.data.dao.InterfaceSpringDataUser;
import spring.data.dao.InterfaceTelefone;
import spring.data.model.Telefone;
import spring.data.model.UsuarioSpringData;

/*Para poder integrar o Junit com o spring*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring-config.xml"})/*le o arquivo de configuração do sppring*/
public class AppSpringDataTest {
	
	@Autowired
	private  InterfaceSpringDataUser interfaceSpringDataUser;
	
	@Autowired
	private  InterfaceTelefone interfaceTelefone;
	
	@Test
	public void testeInsert() {
		UsuarioSpringData usuarioSpringData = new UsuarioSpringData();
		
		usuarioSpringData.setNome("Igor");
		usuarioSpringData.setIdade(18);
		usuarioSpringData.setLogin("mauser");
		usuarioSpringData.setSenha("mapass");
		usuarioSpringData.setEmail("mdmaria@gmail.com");
		
		interfaceSpringDataUser.save(usuarioSpringData);
		
		System.out.println(interfaceSpringDataUser.count());
	}

	@Test
	public void testeConsulta() {
		/*o método (findById(3L)) do spring retorna um Objeto Optional, por isso o objeto
		q vai receber os dados deve ser declarado como Optional<ClasseModelo>*/
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findById(3L);
		
		System.out.println(usuarioSpringData.get().getId());
		System.out.println(usuarioSpringData.get().getNome());
		System.out.println(usuarioSpringData.get().getIdade());
		System.out.println(usuarioSpringData.get().getLogin());
		System.out.println(usuarioSpringData.get().getSenha());
		System.out.println(usuarioSpringData.get().getEmail());
		
	}

	@Test
	public void testeUpdate() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findById(3L);
		
		/*Passa o objeto opcional para um objeto normal, evita o uso do .get() como no exemplo acima
		 pois ele ja pega o opjeto dentro do opcional e passa para um novo*/
		UsuarioSpringData data = usuarioSpringData.get();
		
		data.setNome("Usuario editao");
		
		interfaceSpringDataUser.save(data);
	}
	
	@Test
	public void testeConsultaTodos() {
		/*o método (findAll()) do spring retorna uma lista do tipo Iterable, por isso a lista
		 q vai receber os dados deve ser declarada como Iterable<ClasseModelo>*/
		Iterable<UsuarioSpringData> lista = interfaceSpringDataUser.findAll();
		
		for (UsuarioSpringData usuarioSpringData : lista) {
			System.out.println(usuarioSpringData.getId());
			System.out.println(usuarioSpringData.getNome());
			System.out.println(usuarioSpringData.getIdade());
			System.out.println(usuarioSpringData.getLogin());
			System.out.println(usuarioSpringData.getSenha());
			System.out.println(usuarioSpringData.getEmail());
			System.out.println("------------------------------");
		}		
	}
	
	@Test
	public void testeDelete() {
		/*primeira forma*/
		interfaceSpringDataUser.deleteById(1L);
		
		/*Segunda forma(consultando e deletando objeto inteiro*/
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findById(3L);
		interfaceSpringDataUser.delete(usuarioSpringData.get());
	}
	
	
	@Test
	public void testeConsultaNome() {
		List<UsuarioSpringData> list = interfaceSpringDataUser.buscaPorNome("Igor");
		
		for (UsuarioSpringData usuarioSpringData : list) {
			System.out.println(usuarioSpringData.getId());
			System.out.println(usuarioSpringData.getNome());
			System.out.println(usuarioSpringData.getIdade());
			System.out.println(usuarioSpringData.getLogin());
			System.out.println(usuarioSpringData.getSenha());
			System.out.println(usuarioSpringData.getEmail());
			System.out.println("------------------------------");
		}
	}
	
	@Test
	public void testeConsultaNomeParam() {
		UsuarioSpringData usuarioSpringData = interfaceSpringDataUser.buscaPorNomeParam("Maria");
		
		System.out.println(usuarioSpringData.getId());
		System.out.println(usuarioSpringData.getNome());
		System.out.println(usuarioSpringData.getIdade());
		System.out.println(usuarioSpringData.getLogin());
		System.out.println(usuarioSpringData.getSenha());
		System.out.println(usuarioSpringData.getEmail());
	}
	
	@Test
	public void testeDeleteCondicional() {
		interfaceSpringDataUser.deletePorNome("Maria");
	}
	
	@Test
	public void testeUpdateCondicional() {
		interfaceSpringDataUser.updateEmailPorNome("adsa@gmail.com", "Igor");
	}
	
	@Test
	public void teste() {
		Telefone telefone = new Telefone();
		
		Optional<UsuarioSpringData> user = interfaceSpringDataUser.findById(2L);
		
		telefone.setNumero("31 9 9999-8888");
		telefone.setTipo("CASA");
		telefone.setUsuarioSpringData(user.get());
		
		interfaceTelefone.save(telefone);
	}
}
