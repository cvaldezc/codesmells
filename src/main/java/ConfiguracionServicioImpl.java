package pe.gob.oefa.sij.seguridad.servicio;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pe.gob.oefa.sij.comun.util.Util;
import pe.gob.oefa.sij.seguridad.persistencia.dao.ParametroDAO;
import pe.gob.oefa.sij.seguridad.vo.ParametroVO;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
@Service(value="configuracionServicio")
public class ConfiguracionServicioImpl implements Serializable, ConfiguracionServicio {
	private static final Logger log = Logger.getLogger(ConfiguracionServicioImpl.class);
	private int modular = 0;

	@Autowired
	@Qualifier("parametroInterface")
	private ParametroDAO parametroDAO;
	
	public List<ParametroVO> getParametroByCodigo(List numParametro){
		List<ParametroVO> lista = new ArrayList();
		try{
			lista = parametroDAO.getParametroByCodigo(numParametro);
		}catch(Exception e){
			log.error(e.getMessage(), e.getCause());;
		}
		return lista;
	}

	public String modificarParametro(ParametroDAO parametroDAO){
		return "";
	}
	
	public int modificarParametroEspecifico(Map param) throws Exception{
		Map paramUpdate = new HashMap();
		int rpta = 0;
		
		HttpSession misession= (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		com.debugsac.bean.Usuario usuario = (com.debugsac.bean.Usuario) misession.getAttribute("usuarioBean");
		String userName = usuario.getUserName();
		Timestamp fechaActual = Util.getFechaActual();
		
		if(param.get("numIntentos")!=null && !"".equals(param.get("numIntentos").toString().trim())){
			paramUpdate.clear();
			paramUpdate.put("idParametro", Util.getPropparam().get("codigo_num_autenticacion"));
			paramUpdate.put("codigoParametro", "00000001");
			paramUpdate.put("desFuncion", param.get("numIntentos"));
			paramUpdate.put("usuarioModificacion", userName);
			paramUpdate.put("fechaModificacion", fechaActual);
		
			rpta = parametroDAO.modificarParametroEspecifico(paramUpdate);
		}
		
		if(param.get("diasFecha")!=null && !"".equals(param.get("diasFecha").toString().trim())){
			paramUpdate.clear();
			paramUpdate.put("idParametro", Util.getPropparam().get("codigo_rango_dias_fecha"));
			paramUpdate.put("codigoParametro", "00000001");
			paramUpdate.put("desFuncion", param.get("diasFecha"));
			paramUpdate.put("usuarioModificacion", userName);
			paramUpdate.put("fechaModificacion", fechaActual);
		
			rpta = parametroDAO.modificarParametroEspecifico(paramUpdate);
		}
		
		if(param.get("tamArchivo")!=null && !"".equals(param.get("tamArchivo").toString().trim())){
			paramUpdate.clear();
			paramUpdate.put("idParametro", Util.getPropparam().get("codigo_tam_archivo"));
			paramUpdate.put("codigoParametro", "00000001");
			paramUpdate.put("desFuncion", param.get("tamArchivo"));
			paramUpdate.put("usuarioModificacion", userName);
			paramUpdate.put("fechaModificacion", fechaActual);
			
			rpta = parametroDAO.modificarParametroEspecifico(paramUpdate);
		}
		
		return rpta;
	}
	
	public List<ParametroVO> getParametroDetalle(String id){
		List lista = new ArrayList<ParametroVO>();
		
		try{
			lista = parametroDAO.getParametroDetalle(id);
		}catch(Exception e){
			log.error(e.getMessage(), e.getCause());
			lista = new ArrayList<ParametroVO>();
		}
		
		return lista;
	}

	public List<ParametroVO> getParametroDetalleVI(String id){
		List lista = new ArrayList<ParametroVO>();
		
		try{
			lista = parametroDAO.getParametroDetalle(id);
		}catch(Exception e){
			log.error(e.getMessage(), e.getCause());
			lista = new ArrayList<ParametroVO>();
		}
		
		return lista;
	}
	
}
