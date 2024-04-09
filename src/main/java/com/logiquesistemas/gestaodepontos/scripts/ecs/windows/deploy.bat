@REM TODO verificar esse arquivo preenchendo as coisas abaixo

call build.bat
aws ecs update-service --cluster [SEU_CLUSTER] --service [SEU_SERVICE]  --force-new-deployment --profile [SEU_PROFILE]
