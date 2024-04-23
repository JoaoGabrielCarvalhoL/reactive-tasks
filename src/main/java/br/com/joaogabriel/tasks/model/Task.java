package br.com.joaogabriel.tasks.model;

import br.com.joaogabriel.tasks.client.response.Address;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import org.springframework.data.annotation.Id;


public class Task {

    @Id
    private String id;
    private String title;
    private String description;
    private int priority;
    private TaskState state;
    private Address address;

    public Task() {}

    public Task(String title, String description, int priority, TaskState state) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.state = state;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Task newTask() {
        return this;
    }

    public Task insert() {
        return builderFrom(this)
                .withState(TaskState.INSERT)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builderFrom(Task task) {
        return new Builder(task);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Task updateAddress(Address address) {
        this.address = address;
        return this;
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private int priority;
        private TaskState state;
        private Address address;

        public Builder() {}

        public Builder(Task task) {
            this.id = task.getId();;
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.priority = task.getPriority();
            this.state = task.getState();
            this.address = task.getAddress();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder withState(TaskState state) {
            this.state = state;
            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Task build() {
            return new Task(title, description, priority, state);
        }


    }
}
