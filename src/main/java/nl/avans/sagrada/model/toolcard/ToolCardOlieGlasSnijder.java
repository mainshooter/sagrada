package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

public class ToolCardOlieGlasSnijder extends ToolCard {
    
    public ToolCardOlieGlasSnijder(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        // TODO Auto-generated method stub
        return null;
    }

}