const express = require('express');
const { MongoClient } = require('mongodb');
const cors = require("cors");

const app=express();
app.use(cors());

const uri="mongodb+srv://<username>:<password>@cluster0.meytpgo.mongodb.net/?appName=Cluster0"
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

    app.listen(4000, ()=> console.log("Server running at http://localhost"))
}

main().catch(console.error);