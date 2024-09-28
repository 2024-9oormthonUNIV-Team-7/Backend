# Base image
FROM nginx:latest

# Copy custom Nginx configuration file into container
COPY ./nginx.conf /etc/nginx/nginx.conf

# Copy SSL certificates (make sure these paths are correct)
COPY ../../ssl/fullchain.pem /etc/ssl/certs/fullchain.pem
COPY ../../ssl/privkey.pem /etc/ssl/private/privkey.pem

# Expose HTTP and HTTPS ports
EXPOSE 80
EXPOSE 443