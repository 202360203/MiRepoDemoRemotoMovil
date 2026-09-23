const express = require('express');
const { MongoClient } = require('mongodb');
const cors = require("cors");

const app=express();
app.use(cors());

const uri="mongodb://<username>:<password>@ac-dx5h3an-shard-00-00.meytpgo.mongodb.net:27017,ac-dx5h3an-shard-00-01.meytpgo.mongodb.net:27017,ac-dx5h3an-shard-00-02.meytpgo.mongodb.net:27017/?ssl=true&replicaSet=atlas-u3faar-shard-0&authSource=admin&appName=Cluster0"
const client= new MongoClient(uri);

async function main(){
    await client.connect();
    const db=client.db("sample_mflix");
    const movies = db.collection("movies");
    
    app.get("/movies", async (req, res)=>{
        const data = await movies
        .find({},{projection:{poster:1, title:1, fullplot:1}})
        .limit(60)
        .toArray();
        res.json(data);
    });

    app.listen(4000, ()=> console.log("Server running at http://localhost:4000"))
}

main().catch(console.error);