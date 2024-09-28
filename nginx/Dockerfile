# Base image
FROM nginx:latest

# Copy custom Nginx configuration file into container
COPY ./nginx.conf /etc/nginx/nginx.conf

# Copy SSL certificates (make sure these paths are correct)
COPY /etc/letsencrypt/live/floating.site/fullchain.pem /etc/ssl/certs/fullchain.pem
COPY /etc/letsencrypt/live/floating.site/privkey.pem /etc/ssl/private/privkey.pem

# Expose HTTP and HTTPS ports
EXPOSE 80
EXPOSE 443