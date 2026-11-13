FROM apache/kafka:4.1.2
# El entrypoint propio solo configura la direccion anunciada por Render.
COPY --chmod=755 startup.sh /opt/kafka/startup-render.sh
CMD ["/opt/kafka/startup-render.sh"]
