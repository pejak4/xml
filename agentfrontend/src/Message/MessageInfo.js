import React from 'react';
import axios from '../axios-objects';
import './Messages.css';

class MessageInfo extends React.PureComponent{

    state = {from: '', write: false};

    loadSender = async(id) => {
        const resp = await axios.get('/authentication-service/getUserById',{params: {id: id}});
        if(resp){
            this.setState({from: `${resp.data.firstName} ${resp.data.lastName}`});
        }
        if(this.state.write===false) this.setState({write: true});
        else this.setState({write: false});
    }

    processMessage(id, str){
        if(str==='DELETE') this.props.ret(id, str);
        if(str==='REPLY') this.props.ret2(id);
    }

    buttons(id){
        if(this.state.write===true){
                return (
                    <div>
                    <button className="button button3" onClick={() => {this.processMessage(id, 'DELETE');}}>Delete</button>
                    <button className="button button1" onClick={() => {this.processMessage(this.props.data.senderId, 'REPLY')}}>Reply</button>
                    </div>
                );
        }
        
    }

    render(){
        return (
            <div className="card" key={this.props.data.id} id={this.props.data.senderId} onClick={()=>{ this.loadSender(this.props.data.senderId);}}>
                    <div className='containerr' >
                        <h4><b>{this.props.data.messageDate.split("T")[0]} {this.props.data.messageDate.split("T")[1]}</b></h4>
                        <h4><b>{this.props.data.message}</b></h4>
                        <h4><b>{this.state.write===true ? this.state.from: ''}</b></h4>
                            {this.buttons(this.props.data.id)}
                    </div>
                </div>
        );
    }

}

export default MessageInfo;

