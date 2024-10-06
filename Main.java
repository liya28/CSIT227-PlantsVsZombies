import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        List<Plant> plants = new ArrayList<>();
        
        System.out.print("Game Mode: ");
        String mode = sc.nextLine();
        boolean mush_awake = false;
        if (mode.equals("Night") || mode.equals("Fog"))
            mush_awake = true;
        
        String name = "";
        while (!name.equals("DONE"))
        {
            System.out.print("Add a plant: ");
            name = sc.nextLine();

            if (name.equalsIgnoreCase("DONE")) break;

            switch(name)
            {
                case "Sunflower": plants.add(new Plant.Sunflower()); break;
                case "Twin Sunflower": 
                    Plant.TwinSunflower twin = null;; 
                    int index = -1;
                    for (int i = 0; i < plants.size(); i++)
                    {
                        if (plants.get(i) instanceof Plant.Sunflower)
                        {
                            twin = (Plant.TwinSunflower)((Plant.Sunflower)plants.get(i)).upgrade();
                            index = i; 
                            break;
                        }
                    }
                    if (index != -1) plants.set(index, twin);
                    break;
                case "Peashooter": plants.add(new Plant.Peashooter()); break;
                case "Wall Nut": plants.add(new Plant.WallNut()); break;
                case "Squash": plants.add(new Plant.Squash()); break;
                case "Jalapeno": plants.add(new Plant.Jalapeno()); break;
                case "Coffee Bean":
                    Plant.CoffeeBean cb = new Plant.CoffeeBean();

                    for (Plant p : plants)
                    {
                        if (p instanceof Mushroom && ! ((Mushroom)p).isAwake())
                        {
                            ((Mushroom)p).awaken(cb);
                            break;
                        }
                    }
                    continue;
                case "Sun-shroom": plants.add(new Mushroom.SunShroom(mush_awake)); break;
                case "Puff-shroom": plants.add(new Mushroom.PuffShroom(mush_awake)); break;
                case "Doom-shroom": plants.add(new Mushroom.DoomShroom(mush_awake)); break;
                case "Lily Pad": plants.add(new Plant.LilyPad()); break;
                case "Cattail": 
                    
                    Plant.Cattail cat = null;
                    index = -1;
                    for (int i = 0; i < plants.size(); i++)
                    {
                        if (plants.get(i) instanceof Plant.LilyPad)
                        {
                            cat = (Plant.Cattail)((Plant.LilyPad)plants.get(i)).upgrade();
                            index = i; 
                            break;
                        }
                    }
                    
                    if (index != -1) plants.set(index, cat);
                    
                    break;
                default:
                    System.out.println(name + " is not a plant");
                    continue;
            }
        }
        
        List<Plant> copyplants = new ArrayList<>(plants);
        
        String action = "";
        do 
        {
            System.out.print("Do something: ");
            action = sc.nextLine();
        
            if (action.equals("DONE")) {
                break;
            }
        
            removeDeadPlants(plants); 
        
            int total = 0;
            int count = 0;
            boolean found = false;
            switch(action)
            {
                case "Produce Sun":
                    for (Plant p : plants)
                    {
                        if (p instanceof SunProducer)
                        {
                            total += ((SunProducer)p).produce_sun();
                            count++;
                        }
                    }

                    if (count > 0)
                    {
                        System.out.println(count + " sun producers gather " + total + " suns");
                    }
                    else
                    {
                        System.out.println("You have no sun producers");
                    }
                    break;

                case "Attack":
                    for (Plant p : plants)
                    {
                        if (p instanceof Attacker)
                        {
                            Attacker a = (Attacker)p;
                            total += a.attack();
                            count++;
                        }
                    }
                    
                    if (count > 0) System.out.println(count + " attackers dealing " + total + " damage");
                    else System.out.println("You have no attackers");

                    
                    break;

                case "Instant Kill Status":
                    for (Plant p : plants)
                    {
                        if (p instanceof InstantKiller)
                        {
                            InstantKiller i = (InstantKiller)p;
                            found = true;
                            int type = i.killType();
                            System.out.print(p.name);
                            switch(type)
                            {
                                case 1: System.out.println(" can kill instantly"); break;
                                case 2: System.out.println(" can kill on contact"); break;
                                default: System.out.println("No type like this"); break;
                            }
                        }
                    }
                    if (!found) System.out.println("You have no plants which can kill instantly");
                    break;

                case "Attacker Status":
                    for (Plant p : plants)
                    {
                        if (p instanceof Attacker)
                        {
                            if ( (p.isAlive() && !(p instanceof Mushroom)) || 
                                  (p instanceof Mushroom && ((Mushroom)p).isAwake()) )
                            {
                                Attacker a = (Attacker)p;
                                found = true;
                                int type = a.rangeType();
                                System.out.print(p.name);
                                switch(type)
                                {
                                    case 1:
                                        System.out.println(" can attack on a single line"); break;
                                    case 2:
                                        System.out.println(" can attack using area-of-effect"); break;
                                    case 3:
                                        System.out.println(" can attack only when enemy is nearby"); break;
                                    case 4:
                                        System.out.println(" can attack enemies from anywhere"); break;
                                    default:
                                        System.out.println("No type like this"); break;
                                }
                            }
                        }
                    }
                    if (!found) System.out.println("You have no attackers");
                    break;

                case "Sort by HP":
                    Collections.sort(copyplants, Plant.SortbyHP);
                    for (Plant p : copyplants)
                    {
                        System.out.println(p);
                    }
                    break;

                case "Sort by Name":
                    Collections.sort(copyplants, Plant.SortbyName);
                    for (Plant p : copyplants)
                    {
                        System.out.println(p);
                    }
                    break;

                case "Sort by Sun Cost":
                    Collections.sort(copyplants, Plant.SortbyCost);
                    for (Plant p : copyplants)
                    {
                        System.out.println(p);
                    }
                    break;
            }
        } while (true);
    }
    
    public static void removeDeadPlants(List<Plant> plants)
    {
        plants.removeIf(plant -> !plant.isAlive());
    }
}
