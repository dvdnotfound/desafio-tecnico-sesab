package br.com.teleconsulta.converter;

import br.com.teleconsulta.model.Usuario;
import br.com.teleconsulta.service.UsuarioService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Long id = Long.valueOf(value);
            UsuarioService service = new UsuarioService();
            return service.buscarPorId(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof Usuario) {
            return String.valueOf(((Usuario) value).getId());
        }
        
        return "";
    }
}
