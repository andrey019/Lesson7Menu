import java.util.List;

public interface DBInterface {
    public boolean addEntityToDB(Object...objects);
    public boolean deleteEntityFromBD(Object...objects);
    public List getAllEntities(Class objectClass);
    public Object getEntityById(Class objectClass, long id);
    public Object getEntityByName(Class objectClass, String name);
    public List getEntitiesByPrice(Class objectClass, double from, double to);
    public List getEntitiesWithDiscount(Class objectClass);
    public List randomEntitiesByWeight(Class objectClass, int maxWeight);
    public int getRowCount(Class objectClass);
}
