package com.database.atypon.Node.utils.cache;

import org.json.JSONObject;

public class DoubleLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public DoubleLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void add(Node node){
        if(node == null)
            throw new NullPointerException("Node cannot be null");

        if(head == null){
            head = node;
            tail = node;
            return;
        }
        tail.setNext(node);
        node.setPrev(tail);
        tail = node;
        size++;
    }

    public void remove(Node node){
        if(node == null)
            throw new NullPointerException("Node is null");
        if(head == null)
            throw new IllegalArgumentException("List is empty");
        node.prev.setNext(node.next);
        node.next.setPrev(node.prev);
        node = null;
        size--;
    }

    public void removeHead(){
        if(head == null)
            throw new IllegalArgumentException("List is empty");
        head = head.next;
        head.prev = null;
        size--;
    }

    public Node getHead(){
        return head;
    }

    public Node getTail(){
        return tail;
    }

    public int getSize(){
        return size;
    }

    public class Node{

        private JSONObject value;
        private Node next;
        private Node prev;
        private String key;

        public Node(JSONObject value, String key){
            this.value = value;
            this.key = key;
        }

        public JSONObject getValue() {
            return value;
        }

        public void setValue(JSONObject value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
