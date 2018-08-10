package ondro.btcdataproducer.entity;

public class ApplicationEvent {
    boolean appEvent = true;
    AppEventType event;

    public ApplicationEvent() {
    }

    public ApplicationEvent(AppEventType event) {
        this.event = event;
    }

    public boolean isAppEvent() {
        return appEvent;
    }

    public AppEventType getEvent() {
        return event;
    }

    public void setEvent(AppEventType event) {
        this.event = event;
    }
    
}
