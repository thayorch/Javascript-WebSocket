const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 3000 });

wss.on('connection', (ws) => {
    console.log('A new client connected!');

    // Send a message to the client
    ws.send('Hello from WebSocket server!');

    // Handle messages from the client
    ws.on('message', (message) => {
        console.log('Received message from client:', message.toString());
        // Respond back to the client
        ws.send(`" ${message} " from server!! `);
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});

console.log('WebSocket server is running on ws://localhost:3000');
