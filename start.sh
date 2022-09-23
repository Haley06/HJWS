docker stop fatigue-2021
git pull origin main
yarn build
docker rm -f fatigue-2021
docker run -d --restart=on-failure:5\
    -p 8085:80 \
    -v $PWD/dist:/usr/share/nginx/html \
    --name fatigue-2021 nginx