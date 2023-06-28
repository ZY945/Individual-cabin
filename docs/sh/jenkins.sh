

echo "开始删除和拷贝jar"
./removejar.sh && ./copyjar.sh && cd ../jar/appOauth2 && ./CabinOauth2.sh && cd ../appIM && ./CabinIMBuild.sh && cd ../app && ./appBuild.sh && cd ../admin && ./CabinAdminBuild.sh
