package br.com.teleconsulta.converter;

import br.com.teleconsulta.model.UnidadeSaude;
import br.com.teleconsulta.service.UnidadeSaudeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("unidadeSaudeConverter")
public class UnidadeSaudeConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Long id = Long.valueOf(value);
            UnidadeSaudeService service = new UnidadeSaudeService();
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
        
        if (value instanceof UnidadeSaude) {
            return String.valueOf(((UnidadeSaude) value).getId());
        }
        
        return "";
    }
}
