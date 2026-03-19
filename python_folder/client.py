import socket

HOST = "127.0.0.1"
PORT = 5000 # 5000 (Python) 6000 (Java)

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect((HOST, PORT))

print("Cliente Python ligado. Ctrl+C para sair.")

try:
    while True:
        msg = input("Mensagem: ")
        client.send((msg + "\n").encode())
        response = client.recv(1024).decode()
        print("Resposta:", response)
except KeyboardInterrupt:
    print("\nCliente fechado")
finally:
    client.close()