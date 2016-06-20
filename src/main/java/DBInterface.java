import java.util.List;

public interface DBInterface {
    public boolean addEntityToDB(Object...objects);
    public boolean deleteEntityFromBD(Object...objects);
    public Object getAllEntities(Object object);
    public Object getEntityById(Object object);
    public List getEntitiesByPrice(Object object, double from, double to);
    public List getEntitiesWithDiscount(Object object);
    public List randomEntitiesByWeight(Object object, int maxWeight);
}
