cd ~/projetos/sesab/teleconsulta-salas/src/main/java/br/com/teleconsulta/controller

# Backup dos arquivos
cp UsuarioBean.java UsuarioBean.java.bak
cp PacienteBean.java PacienteBean.java.bak
cp UnidadeSaudeBean.java UnidadeSaudeBean.java.bak
cp SalaBean.java SalaBean.java.bak
cp ReservaBean.java ReservaBean.java.bak

# Substituir imports em todos os arquivos
for file in *Bean.java; do
    sed -i 's/import javax.faces.bean.ManagedBean;/import javax.inject.Named;/g' "$file"
    sed -i 's/import javax.faces.bean.ViewScoped;/import javax.faces.view.ViewScoped;/g' "$file"
    sed -i 's/@ManagedBean/@Named/g' "$file"
done

echo "Arquivos corrigidos!"
