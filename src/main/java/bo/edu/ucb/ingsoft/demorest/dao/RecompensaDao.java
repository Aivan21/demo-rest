package bo.edu.ucb.ingsoft.demorest.dao;

import bo.edu.ucb.ingsoft.demorest.dto.RecompensaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecompensaDao {
    @Autowired
    private SequenceDao sequenceDao;
    @Autowired
    private DataSource dataSource;

    public RecompensaDTO crearRecompensa(RecompensaDTO recompensaDTO){
        recompensaDTO.setIdRecompensa(sequenceDao.getPrimaryKeyForTable("recompensa"));
        Connection conn=null;
        try{
            conn= dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO recompensa VALUES (?,?,?,?,?)");
            pstmt.setInt(1,recompensaDTO.getIdRecompensa());
            pstmt.setInt(2,recompensaDTO.getRangoInicial());
            pstmt.setInt(3,recompensaDTO.getRangoFinal());
            pstmt.setString(4,recompensaDTO.getRecompensa());
            pstmt.setInt(5,recompensaDTO.getIdProyecto());
            pstmt.executeUpdate();
            pstmt.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return recompensaDTO;
    }



    public RecompensaDTO findRecompensaById (Integer idrecompensa){
        RecompensaDTO result = new RecompensaDTO();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt =conn.prepareStatement("SELECT id_recompensa, valor_min,valor_max,recompensa,id_proyecto FROM recompensa WHERE id_recompensa = ?")
        ){
            pstmt.setInt(1,idrecompensa);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                result.setIdRecompensa(rs.getInt("id_recompensa"));
                result.setRangoInicial(rs.getInt("valor_min"));
                result.setRangoFinal(rs.getInt("valor_max"));
                result.setRecompensa(rs.getString("recompensa"));
                result.setIdProyecto(rs.getInt("id_proyecto"));
            }else {
                result= null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public List<RecompensaDTO>findallRecompensa(){
        List<RecompensaDTO> result = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_recompensa, valor_min,valor_max,recompensa,id_proyecto FROM recompensa ");
            while(rs.next()){
                RecompensaDTO recompensaDTO=new RecompensaDTO();
                recompensaDTO.setIdRecompensa(rs.getInt(1));
                recompensaDTO.setRangoInicial(rs.getInt(2));
                recompensaDTO.setRangoFinal(rs.getInt(3));
                recompensaDTO.setRecompensa(rs.getString(4));
                recompensaDTO.setIdProyecto(rs.getInt(5));
                result.add(recompensaDTO);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}


