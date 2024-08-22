package org.extras;

/**
 * Methods will be called every tick so implementations that set class Timer attribute
 * Should utilize frames per second
 * @author      Xander Smith
 * @version     1.0
 * @since       0.2
 */
public interface Timer {
    public boolean isEvent();

    public void setTimer();

    public void tick();

    public void timerMethod();
}
