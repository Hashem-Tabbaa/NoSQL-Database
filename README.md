
# Decentralized Cluster-Based NoSQL DB System
## (Atypon Capstone Project)


## Project Description
A cluster-based NoSQL DB is a collection of nodes that
can service multiple users, where each node has a replica of the DB. NoSQL DB
clusters can have a manager node that works as a central point in the system. We refer
to such systems as centralized systems. On the other hand, NoSQL DB systems can be
decentralized, where there is no manager node, and instead the nodes in the DB rely on
sophisticated schemes for ensuring data consistency and load balance. In this project,
you are required to use Java to build an application that simulates the interaction
between users and nodes inside a decentralized NoSQL DB cluster. Below, we first
describe the application requirements, then we discuss important highlights in the
implementation.

## System Requirements
- Each database node should be represented as a Docker container.
- Initially, there must be a cluster bootstrapping step, which is responsible for starting up the cluster and initiating all nodes.
- If there is a new user, then this user will first communicate with the bootstrapping node to obtain login information and its assigned node.
- Nodes should be capable of verifying login information for users to minimize security risks.
- After successfully logging to their assigned nodes, users can now send queries to the database.
- The database is a document-based database that uses JSON objects to store documents.
- Each DB has a schema, and each document within a DB has a JSON schema that belongs to the DB schema.
- DB queries should include creating or deleting a DB, creating or deleting a document within a DB, and reading or writing specific json properties within a document.
- A document (i.e., json object) has an ID which should be unique and indexed in an efficient manner.
- Create indexes on a single json property.
- Data, schemas, indexes are replicated across all nodes, i.e., stored inside the local file system of each node.
- Note that you need another hash function to assign affinity between documents to nodes while taking load-balance into consideration.
- Reads to JSON properties are the majority of transactions, while writes are rare. - There is at least one pre-determined DB admin
