package graphics_control.event_handling.controllers_events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * GameOverEvent Class, extends Event.
 */
public class GameOverEvent extends Event {
    private GameOverEventArguments args;

    public static final EventType<GameOverEvent> CUSTOM = new EventType(ANY, "CUSTOM");

    public GameOverEvent(GameOverEventArguments args) {
        super(GameOverEvent.CUSTOM);
        this.args = args;
    }

    /**
     * getArgs().
     *
     * @return the GameOverEventArguments held by this event.
     */
    public GameOverEventArguments getArgs() {
        return args;
    }
}
