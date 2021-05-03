package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "cliente")
public class Cliente extends Pessoa {
	
	@Column(name = "nome")
	private String nome;

	@Column(name = "ativo")
	private Boolean ativo; 

	@Column(name = "dt_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(name = "email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_cliente")
	private TipoCliente tipoCliente;
	
	@ManyToOne
	@JoinColumn(name = "id_genero")
	private Genero genero;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="cliente_endereco",
			joinColumns = @JoinColumn(name = "id_cliente"),
			inverseJoinColumns = @JoinColumn(name = "id_endereco")
	)
	private List<Endereco> enderecos;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="cliente_cartao",
			joinColumns = @JoinColumn(name = "id_cliente"),
			inverseJoinColumns = @JoinColumn(name = "id_cartao")
	)
	private List<Cartao> cartoes;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="telefone_cliente",
			joinColumns = @JoinColumn(name = "id_cliente"),
			inverseJoinColumns = @JoinColumn(name = "id_telefone")
	)
	private List<Telefone> telefones;
	
	@OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private Carrinho carrinho;
	
	@OneToMany(mappedBy = "cliente")
	private List<CupomCliente> cuponsCliente;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public List<CupomCliente> getCuponsCliente() {
		return cuponsCliente;
	}

	public void setCuponsCliente(List<CupomCliente> cuponsCliente) {
		this.cuponsCliente = cuponsCliente;
	}
}
