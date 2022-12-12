package history;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Node<Task>> history = new ArrayList<>();
    private HashMap<Integer, Node<Task>> historyMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    @Override
    public void add(Task task) {

    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyTaskList = new ArrayList<>();
        for (Node<Task> taskNode : history) {
            historyTaskList.add(taskNode.data);
        }
        return historyTaskList;
    }

    void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(task, oldTail, null);
        tail = newNode;
        if(oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
    }

    void getTasks() {
        history.clear();
        Node<Task> nextNode = head;
        while (nextNode != null) {
            history.add(nextNode);
            nextNode = nextNode.next;
        }
    }

    void removeNode(Node<Task> node) {
        Node<Task> nodeToDel = historyMap.get(node.data.getId());
        Node<Task> prevNode = nodeToDel.prev;
        Node<Task> nextNode = nodeToDel.next;
        if (prevNode != null)
            prevNode.next = nextNode;
        if(nextNode != null)
            nextNode.prev = prevNode;
    }

}

class Node<T> {
    T data;
    Node<T> prev;
    Node<T> next;

    public Node(T data, Node<T> prev, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
