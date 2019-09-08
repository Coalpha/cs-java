reset = "\033[0m"
pink = "\033[35m"
cyan = "\033[36m"

Signal::INT.trap {
  puts "\n#{pink}Interrupt! Exiting Gracefully...#{reset}"
  exit 0
}

class Fraction
  def initialize(@top : UInt16, @bot : UInt16)
  end
  def negate
    @top *= -1
  end
  def initialize(i : UInt16)
    @top = i
    @bot = i
  end
  def +(f : Fraction)
    Fraction.new(
      @top * f.bot + f.top * @bot,
      @bot * f.bot
    )
  end  
end

def solve(input : String)
  parts = input.split(' ')
  return parts
end

while (true)
  print "#{cyan}$ #{reset}"
  input = gets
  if (input != nil)
    input = input.as(String)
    if (input.=~ /\.?[qQeE]|quit|exit/)
      exit 0
    else
      puts solve(input)
    end
  end
end
