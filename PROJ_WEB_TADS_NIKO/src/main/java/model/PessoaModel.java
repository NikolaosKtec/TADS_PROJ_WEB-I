//package model;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.util.List;
//
//@Repository
//public class PessoaModel {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//
//
//    private List<Pessoa> pessoas;
//
//
//
//    //testar metodo
//    public void insert_Pessoa(Pessoa ps) {
//        String nome = ps.getNome();
//        String email = ps.getEmail();
//        String senha = ps.getSenha();
//        Boolean e_lojista = ps.getElojista();
//
//        final String sql = "INSERT INTO Pessoa() values("+nome+","+email+","+senha+","+e_lojista+")";
//        jdbcTemplate.update(sql);
//
//    }
//
//
//    public Pessoa select_pessoa(String email) {
//        Pessoa ps = new Pessoa();
//
//        final String sql = "SELECT * FROM Pessoa WHERE email= "+"'"+email+"'";
//       // try{
//
//
//        jdbcTemplate.query(sql, (ResultSet rs) -> {
//
//                ps.setNome(rs.getString("nome"));
//                ps.setEmail(rs.getString("email"));
//                ps.setSenha(rs.getString("senha"));
//                ps.setElojista(rs.getBoolean("e_lojista"));
//            });
//       // }catch(NullPointerException e){
//        //    ps.setNome("null");
//         //   ps.setEmail("null");
//       // }
//        return ps;
//    }
////testar método
//    public List<Pessoa> select_all_Pessoas() {
//        Pessoa ps = new Pessoa();
//
//        final String sql = "SELECT * FROM Pessoa";
//        this.jdbcTemplate.query(sql, (ResultSet rs) -> {
//            if (rs.next()) {
//                ps.setNome(rs.getString("nome"));
//                ps.setEmail(rs.getString("email"));
//                ps.setSenha(rs.getString("senha"));
//                ps.setElojista(rs.getBoolean("e_logista"));
//
//                pessoas.add(ps);
//            }
//        });
//        return pessoas;
//    }
//    //testar metodos
//    public void update_email(String email){
//        String sql = "UPDATE Pessoa SET email = "+email+" WHERE email = "+email;
//        this.jdbcTemplate.update(sql);
//    }
//
//    public void update_senha(String email,String senha){
//        String sql = "UPDATE Pessoa SET senha = "+senha+" WHERE email = "+email;
//        this.jdbcTemplate.update(sql);
//    }
//
//    public void remove_pessoa(String email){
//        String sql = "DELETE FROM Pessoa WHERE email = "+email;
//        this.jdbcTemplate.update(sql);
//    }
//}
