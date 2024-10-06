import java.util.Comparator;

public abstract class Plant
{
    String name;
    int hp;
    int sun_cost;
    public static final int INFINITE_HP = Integer.MAX_VALUE;

    Plant(String name, int sun_cost)
    {
        this.name = name;
        hp = 6;
        this.sun_cost = sun_cost;
    }

    Plant(String name, int hp, int sun_cost)
    {
        this.name = name;
        this.hp = hp;
        this.sun_cost = sun_cost;
    }

    // Returns true if its hp is > 0
    public boolean isAlive()
    {
        return hp > 0;
    }

    public String die()
    {
        hp = 0;
        return name + " dies";
    }

    // Returns a string of its name and " dies"
    // To be overridden by InstantKiller classes on whether they die " while exploding" or others
    // Will override the toString method that shall output the name,
    // followed by the hp separated by space enclosed with a (),
    // and lastly the sun cost prepended by " - cost: "
    @Override
    public String toString()
    {
        return name + " (" + ((hp == INFINITE_HP) ? "∞" : hp) + ") - cost: " + sun_cost;
    }

    public static class Sunflower extends Plant implements SunProducer, Upgradable
    {
        public Sunflower()
        {
            super("Sunflower", 50);
        }

        @Override
        public int produce_sun()
        {
            System.out.println(name + " produces 25 suns");
            return 25;
        }

        @Override
        public PlantUpgrade upgrade()
        {
            return new TwinSunflower();
        }
    }

    public static class TwinSunflower extends Plant implements SunProducer, PlantUpgrade
    {
        public TwinSunflower()
        {
            super("Twin Sunflower", 250);
        }

        @Override
        public int produce_sun()
        {
            System.out.println(name + " produces 50 suns");
            return 50;
        }

        @Override
        public int concurrentSunCost()
        {
            return 50;
        }
    }

    public static class Peashooter extends Plant implements Attacker
    {
        public Peashooter() {
            super("Peashooter", 100);
        }

        @Override
        public int attack()
        {
            System.out.println(name + " attacks");
            return 1;
        }

        @Override
        public int rangeType()
        {
            return 1;
        }
    }

    public static class WallNut extends Plant
    {
        public WallNut()
        {
            super("Wall Nut", 25, 50);
        }
    }

    public static class Squash extends Plant implements InstantKiller, Attacker
    {
        public Squash()
        {
            super("Squash", INFINITE_HP, 50);
        }

        public int killType()
        {
            return 2;
        }

        public int attack()
        {
            System.out.println(name + " attacks");
            System.out.println(die());
            hp = 0;
            return 3;
        }

        public int rangeType()
        {
            return 3;
        }

        @Override
        public String die()
        {
            return super.die() + " while squashing zombies";
        }
    }

    public static class Jalapeno extends Plant implements InstantKiller, Attacker
    {
        public Jalapeno()
        {
            super("Jalapeno", INFINITE_HP, 125);
        }

        @Override
        public int attack()
        {
            System.out.println(name + " attacks");
            System.out.println(die());
            hp = 0;
            return 5;
        }

        @Override
        public int rangeType()
        {
            return 1;
        }

        @Override
        public int killType()
        {
            return 1;
        }
        
        @Override
        public String die()
        {
            return super.die() + " while exploding";
        }
    }

    public static class CoffeeBean extends Plant
    {
        public CoffeeBean()
        {
            super("Coffee Bean", INFINITE_HP, 75);
        }

        @Override
        public String die()
        {
            return super.die();
        }
    }

    public static class LilyPad extends Plant implements Upgradable
    {
        public LilyPad()
        {
            super("Lily Pad", 25);
        }

        @Override
        public PlantUpgrade upgrade()
        {
            return new Cattail();
        }
    }

    public static class Cattail extends Plant implements Attacker, PlantUpgrade
    {
        public Cattail()
        {
            super("Cattail", 225);
        }

        @Override
        public int attack()
        {
            System.out.println(name + " attacks");
            return 1;
        }

        @Override
        public int rangeType()
        {
            return 4;
        }

        @Override
        public int concurrentSunCost()
        {
            return 25;
        }
    }
    
    public static Comparator<Plant> SortbyName = new Comparator<Plant>()
    {
        public int compare(Plant p1, Plant p2)
        {
            return p1.name.compareTo(p2.name);
        }
    };
    
    public static Comparator<Plant> SortbyHP = new Comparator<Plant>()
    {
        @Override
        public int compare(Plant p1, Plant p2)
        {
            if (p1.hp != p2.hp) return Integer.compare(p2.hp, p1.hp);
            return p1.name.compareTo(p2.name);
        }
    };
    
    public static Comparator<Plant> SortbyCost = new Comparator<Plant>()
    {
        @Override
        public int compare(Plant p1, Plant p2)
        {
            if (p1.sun_cost != p2.sun_cost) return Integer.compare(p2.sun_cost, p1.sun_cost);
            return p1.name.compareTo(p2.name);
        }
    };
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant p = (Plant)o;
        return name.equals(p.name);
    }
}
