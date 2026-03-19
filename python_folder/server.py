import socket
import threading

HOST = "0.0.0.0"
PORT = 5000 # 5000 (Python) 6000 (Java)

def handle_client(conn, addr):
    print(f"Cliente ligado: {addr}")
    try:
        while True:
            data = conn.recv(1024).decode().strip()
            if not data:
                break
            print(f"Recebido de {addr}: {data}")
            conn.send((data.upper() + "\n").encode())
    except Exception as e:
        print(f"Cliente {addr} desligou inesperadamente: {e}")
    finally:
        conn.close()
        print(f"Cliente {addr} desligou")

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST, PORT))
server.listen()
print(f"Servidor Python a correr na porta {PORT}...")

while True:
    conn, addr = server.accept()
    thread = threading.Thread(target=handle_client, args=(conn, addr))
    thread.start()