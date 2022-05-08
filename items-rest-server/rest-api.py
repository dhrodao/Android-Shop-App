from flask import Flask
import json
app = Flask(__name__)

items = [
    {
        "id":0,
        "type":"FRUIT",
        "name":"Piña",
        "price":1.5,
    },
    {
        "id":1,
        "type":"FRUIT",
        "name":"Uva",
        "price":1.5,
    },
    {
        "id":2,
        "type":"FRUIT",
        "name":"Pera",
        "price":1.5,
    },
    {
        "id":3,
        "type":"FRUIT",
        "name":"Naranja",
        "price":2.00,
    },
    {
        "id":4,
        "type":"SPORT",
        "name":"Chándal",
        "price":50.00,
    },
    {
        "id":5,
        "type":"SPORT",
        "name":"Toalla",
        "price":20,
    },
    {
        "id":6,
        "type":"SPORT",
        "name":"Zapatillas",
        "price":150,
    },
    {
        "id":7,
        "type":"BUTCHER",
        "name":"Ternera",
        "price":3,
    },
    {
        "id":8,
        "type":"BUTCHER",
        "name":"Pollo",
        "price":4,
    },
    {
        "id":9,
        "type":"BUTCHER",
        "name":"Gallina",
        "price":5,
    },
    {
        "id":10,
        "type":"FISH",
        "name":"Trucha",
        "price":2,
    },
    {
        "id":11,
        "type":"FISH",
        "name":"Salmón",
        "price":2,
    },
    {
        "id":12,
        "type":"FISH",
        "name":"Atún",
        "price":2,
    },
]

@app.route('/items')
def hello_world():
    return json.dumps(items, ensure_ascii=False)