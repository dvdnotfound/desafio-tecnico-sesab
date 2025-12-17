# Imagem base do WildFly 26
FROM quay.io/wildfly/wildfly:26.1.3.Final-jdk11

# Informações do mantenedor
LABEL maintainer="contato.dvdsantos@gmail.com"
LABEL description="Sistema de Gerenciamento de Salas de Espera para Teleconsulta"

# Mudar para usuário root para criar diretórios
USER root

# Criar diretórios necessários
RUN mkdir -p /opt/jboss/wildfly/standalone/deployments && \
    mkdir -p /opt/jboss/wildfly/standalone/data && \
    mkdir -p /opt/jboss/wildfly/standalone/log && \
    chown -R jboss:jboss /opt/jboss/wildfly/standalone

# Voltar para usuário jboss
USER jboss

# Variáveis de ambiente
ENV WILDFLY_HOME /opt/jboss/wildfly
ENV DEPLOYMENT_DIR ${WILDFLY_HOME}/standalone/deployments

# Copiar o WAR para o diretório de deployment
COPY --chown=jboss:jboss target/teleconsulta-salas.war ${DEPLOYMENT_DIR}/

# Expor portas
EXPOSE 8080 9990

# Comando para iniciar o WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
