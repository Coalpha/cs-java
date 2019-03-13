import acm.program.*;
import acm.graphics.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.awt.event.*;

/**
 * A better graphics program that uses functional paradigms
 */
abstract class Garfield extends Program {
  private static final long serialVersionUID = 0xdeadbeef;
  protected GCanvas ctx = new GCanvas();

  abstract public void init();

  abstract public void run();

  private List<GObject> objects = new ArrayList<>();

  /** An interface of <code>EventEmitters</code> */
  public interface on {
    // Window Stuff
    EventEmitter<ComponentEvent> show = new EventEmitter<>();
    EventEmitter<ComponentEvent> resize = new EventEmitter<>();
    EventEmitter<ComponentEvent> move = new EventEmitter<>();
    EventEmitter<ComponentEvent> hide = new EventEmitter<>();
    // Mouse Stuff
    EventEmitter<MouseEvent> mouseUp = new EventEmitter<>();
    EventEmitter<MouseEvent> mouseDown = new EventEmitter<>();
    EventEmitter<MouseEvent> mouseExit = new EventEmitter<>();
    EventEmitter<MouseEvent> mouseEnter = new EventEmitter<>();
    EventEmitter<MouseEvent> mouseClick = new EventEmitter<>();
    EventEmitter<MouseEvent> mouseMove = new EventEmitter<>();
    EventEmitter<MouseEvent> mouseDrag = new EventEmitter<>();
  }

  /** WindowEars listens for events that happen to the Window */
  private class WindowEars implements ComponentListener {
    @Override
    public void componentShown(ComponentEvent e) {
      on.show.emit(e);
    }

    @Override
    public void componentResized(ComponentEvent e) {
      on.resize.emit(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
      on.move.emit(e);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
      on.hide.emit(e);
    }
  }

  /**
   * The Mouser class allows Garfield (and friends) to listen and catch mouse
   * related events
   */
  private class Mouser implements MouseListener, MouseMotionListener {
    @Override
    public void mouseReleased(MouseEvent e) {
      on.mouseUp.emit(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      on.mouseDown.emit(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
      on.mouseExit.emit(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      on.mouseEnter.emit(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      on.mouseClick.emit(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      on.mouseMove.emit(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      on.mouseDrag.emit(e);
    }
  }

  protected Garfield() {
    super();
    ctx.addMouseListener(new Mouser());
    add(ctx, "Center");
    validate();
    addComponentListener(new WindowEars());
  }

  /** Clears the canvas of all objects */
  public void clear() {
    ctx.removeAll();
  }

  public List<GObject> getObjects() {
    return objects;
  }

  public void forEachObject(Consumer<GObject> fn) {
    for (GObject gob : ctx) {
      fn.accept(gob);
    }
  }

  public static void start(Garfield garfunkle) {
    garfunkle.setStartupObject(null);
    garfunkle.start();
  }

  public static void main(String[] args) {
    throw new RuntimeException(
      "You didn't declare the main method. Just copy this and replace __CLASS__ with your class name:\n"
      + "  public static void main(String[] args) {\n"
      + "    __CLASS__.start(new __CLASS__());\n"
      + "  }"
    );
  }
}
