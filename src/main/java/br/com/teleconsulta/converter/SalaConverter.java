package br.com.teleconsulta.converter;

import br.com.teleconsulta.model.Sala;
import br.com.teleconsulta.service.SalaService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("salaConverter")
public class SalaConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Long id = Long.valueOf(value);
            SalaService service = new SalaService();
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
        
        if (value instanceof Sala) {
            return String.valueOf(((Sala) value).getId());
        }
        
        return "";
    }
}
