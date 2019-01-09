# Create uaa service cmd
cf create-service xsuaa application xsuaa -c '{"xsappname":"cfsso","tenant-mode":"dedicated"}'